/*
*  Description: java pipeline as code
*  Date: 2019-04-23 16:14
*  Author: gaowei
*/
def call(Map map) {
    pipeline {

        agent {
            label 'swarm'
        }

        options {
            buildDiscarder(logRotator(numToKeepStr: '50'))
            disableConcurrentBuilds()
            timeout(time: 20, unit: 'MINUTES')
        }

        environment {
            APP = "${map.app}"
            LANG = "${map.lang}"
            APP_PORT = "${map.appPort}"
            GROUP= "${map.group}"
            ARTIFACT = "${map.artifact}"
            NS = "${map.namespace}"

            PORTAL_TOKEN = credentials("portal")

            IMAGE_NAME = "${HARBOR}/library/${JOB_NAME}:${BUILD_ID}"
        }

        parameters {
            choice(name: 'BUILD_BRANCH', choices: 'master\ndev\nrelease', description: '请选择分支:')
            choice(name: 'BUILD_ENV', choices: 'dev\nsit\nuat', description: '请选择部署环境:')
        }

        stages {

            stage('认证') {
                when {
                    anyOf {
                        environment name: 'BUILD_ENV', value: 'sit'
                        environment name: 'BUILD_ENV', value: 'uat'
                    }
                }
                steps {
                    auth()
                }
            }

            stage('拉取代码') {
                steps {
                    script {
                        log.debug("选择的分支: ${params.BUILD_BRANCH}")
                        log.debug("部署环境: ${params.BUILD_ENV}")
                        new com.sxh.AppMeta().getJavaOpts(map, "${env.BUILD_ENV}")
                        log.debug("App 元数据: ${map}")
                        git branch: params.BUILD_BRANCH, credentialsId: 'gitlab', url: map.git
                    }
                }
            }
            
            stage('拉取配置') {
                when {
                    expression {
                        return isBinaryConfig(map)
                    }
                }
                steps {
                    script {
                        sh "git clone -b ${params.BUILD_ENV} http://gitlab.shixhlocal.com/devops/config.git"
                    }
                }
            }

            stage('编译') {
                steps {
                    script {
                        mvn { settings ->
                            def cmd = isDev() ? 'package' : 'deploy'
                            sh "mvn -s ${settings} clean ${cmd} -B -Dfile.encoding=UTF-8 -Dmaven.test.skip=true -U"
                        }
                    }
                }
            }

            stage('Sonar分析') {
                when {
                    expression {
                        return !isDev() && false
                    }
                }
                steps {
                    script {
                        withSonarQubeEnv('sonar'){
                            docker.image('mercuriete/sonar-scanner:3.2.0.1227').inside('-v /root/.sonar:/root/.sonar') {
                                sh "sonar-scanner -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=${SONAR_AUTH_TOKEN}  -Dsonar.projectKey=${env.APP} -Dsonar.projectName=${env.APP} -Dsonar.sources=. -Dsonar.java.binaries=."
                            }
                        }
                    }
                }
            }

            stage('单元测试') {
                when {
                    expression {
                        return false
                    }
                }
                steps {
                    allure([
                        disabled: false,
                        includeProperties: false,
                        jdk: '',
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'output/allure']]
                    ])
                }
            }

            stage('自动化测试') {
                failFast true
                parallel {
                    stage('UI自动化测试') {
                        steps {
                            echo "并行1"
                        }
                    }
                    stage('性能自动化测试') {
                        steps {
                            echo "并行二"
                        }
                    }
                    stage('接口自动化测试') {
                        steps {
                            echo "In stage Nested 1 within Branch C"
                        }
                    }
                }
            }

            stage('推送镜像') {
                steps {
                    script {
                        def isBinaryConfig = isBinaryConfig(map)
                        def dockerfile = isBinaryConfig ? 'dockerfile_config' : 'dockerfile'

                        configFileProvider([configFile(fileId: dockerfile, variable: 'DOCKER_FILE')]) {
                            docker.withRegistry("$HARBOR_URL", "harbor") {
                                def args = "--no-cache --build-arg JAR_PATH=${ARTIFACT} --build-arg JAR_NAME=${APP} "
                                if (isBinaryConfig) {
                                    args = args + "--build-arg CONFIG_PATH=config/${env.APP}"
                                }
                                log.debug("args = ${args}")

                                def app = docker.build("$IMAGE_NAME", "${args} -f ${DOCKER_FILE} .")
                                app.push()
                            }
                        }
                        sh "docker rmi -f $IMAGE_NAME"
                    }
                }
            }

            stage("K8S部署") {
                steps{
                    configFileProvider([configFile(fileId: "${params.BUILD_ENV}-config", variable: 'config')]) {
                        sh "docker run --rm -v ${config}:/.kube/config bitnami/kubectl:1.15 -n ${env.NS} set image deployment ${env.APP} ${env.APP}=${IMAGE_NAME}"
                    }
                }
            }

            stage("Ansible部署") {
                when {
                    expression {
                        return !isSit() && false
                    }
                }
                steps{
                    script{
                        docker.image('harbor.shixhlocal.com/library/ansible:centos7').inside() {
                            checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false,
                                      extensions: [], submoduleCfg: [],
                                      userRemoteConfigs: [[credentialsId: 'gitlab', url: 'http://gitlab.shixhlocal.com/devops/jenkins-ansible-playbooks.git']]])
                            ansiColor('xterm') {
                                ansiblePlaybook(
                                    playbook: "playbook_${env.LANG}.yml",
                                    inventory: "hosts/${params.BUILD_ENV}.ini",
                                    hostKeyChecking: false,
                                    colorized: true,
                                    extraVars: [
                                        lang: "${env.LANG}",
                                        app: [value: "${env.APP}", hidden: false],
                                        group: [value: "${env.GROUP}", hidden: false],
                                        appPort: [value: "${env.APP_PORT}", hidden: false],
                                        javaOpts: "${map.javaOpts}",
                                        portArgs: "${map.portArgs}",
                                        env: [value: "${params.BUILD_ENV}", hidden: false],
                                        artifact: "${env.IMAGE_NAME}"
                                    ]
                                )
                            }
                        }
                    }
                }
            }

            stage('同步阿里云') {
                when {
                    expression {
                        return isUat()
                    }
                }
                steps {
                    script {
                        def response = httpRequest(
                            url: "${env.JENKINS_URL}/view/PRD/job/aliyun-harbor/buildWithParameters?token=${env.PORTAL_TOKEN}&app=${env.APP}&imageId=${BUILD_ID}",
                            httpMode: 'GET'
                        )
                        println('Status: '+response.status)
                        println('Response: '+response.content)
                    }
                }
            }

        }

        post {
            always {cleanWs()}
        }

    }
}