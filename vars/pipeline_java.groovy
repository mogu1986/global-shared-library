/*
*  Description: java pipeline as code
*  Date: 2019-04-23 16:14
*  Author: gaowei
*/
def call(Map map) {
    pipeline {

        agent {
            label 'master'
        }

        options {
            buildDiscarder(logRotator(numToKeepStr: '10'))
            disableConcurrentBuilds()
            timeout(time: 20, unit: 'MINUTES')
        }

        environment {
            APP = "${map.app}"
            LANG = "${map.lang}"
            SONAR_SOURCES = "${map['sonar.sources']}"
            SONAR_JAVA_BINARIES = "${map['sonar.java.binaries']}"
            SONAR_LOGIN = credentials("${APP}-sonar-login")

            // 容器相关配置
            IMAGE_NAME = "${HARBOR}/library/${JOB_NAME}:${BUILD_ID}"
            K8S_CONFIG = credentials('k8s-config')

            TEST_DEPLOY_PWD = credentials("TEST_DEPLOY_PWD")
            UAT_DEPLOY_PWD = credentials("UAT_DEPLOY_PWD")
        }

        parameters {
            choice(name: 'BUILD_BRANCH', choices: 'dev\ntest', description: '请选择部署的环境')
        }

        stages {

            stage('env') {
                steps {
                    script {
                        withSonarQubeEnv('sonar'){
                            sh 'printenv'
                        }
                    }
                }
            }


            stage('输入密钥') {
                when {
                    anyOf {
                        environment name: 'BUILD_BRANCH', value: 'test'
                        environment name: 'BUILD_BRANCH', value: 'uat'
                    }
                }
                steps {
                    script {
                        def pre_pwd = ''
                        def env_text = ''

                        if (params.BUILD_BRANCH == 'test') {
                            pre_pwd = "${env.TEST_DEPLOY_PWD}"
                            env_text = '测试'
                        } else {
                            pre_pwd = "${env.UAT_DEPLOY_PWD}"
                            env_text = '预发'
                        }

                        inputParam = input (
                                message: "即将发布到${env_text}环境，请输入密钥:",
                                ok: "确定",
                                submitter: "admin,gaowei",
                                parameters: [
                                        password(name: 'DEPLOY_PWD', defaultValue: '', description: '')
                                ]
                        )
                        if ("${inputParam}" == "${pre_pwd}") {
                            log.debug("密钥正确, 任务将继续执行")
                        } else {
                            throw new GroovyRuntimeException('密码错误')
                        }
                    }
                }
            }

            stage('拉取代码') {
                steps { git branch: params.BUILD_BRANCH, credentialsId: 'gitlab', url: GIT_URL }
            }

            stage('编译') {
                steps {
                    mvn { settings ->
                        sh "mvn -s ${settings} clean deploy -B -Dfile.encoding=UTF-8 -Dmaven.test.skip=true -U"
                    }
                }
            }

            stage('Sonar分析') {
                steps {
                    script {
                        def sonarHome = tool name: 'SonarQube Scanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
                        withSonarQubeEnv('sonar'){
                            sh "${sonarHome}/bin/sonar-scanner -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=${SONAR_LOGIN} -Dsonar.projectKey=${env.APP} -Dsonar.projectName=${env.APP} -Dsonar.sources=${env.SONAR_SOURCES} -Dsonar.java.binaries=${env.SONAR_JAVA_BINARIES}"
                        }
                    }
                }
            }

            stage('单元测试') {
                steps {
                    echo "未开放"
                }
            }

            stage('自动化测试') {
                when {
                    expression { return params.BUILD_BRANCH == 'test'}
                }
                failFast true
                parallel {
                    stage('UI自动化测试') {
                        steps {
                            echo "并行一"
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
                        docker.withRegistry("$HARBOR_URL", "harbor") {
                            def app = docker.build("$IMAGE_NAME")
                            app.push()
                            app.push('latest')
                        }
                        sh "docker rmi -f $IMAGE_NAME"
                    }
                }
            }

            stage('kubernetes部署') {
                steps {
                    script {
                        docker.image('harbor.top.mw/library/kubectl:1.7.6').inside() {
                            sh "mkdir -p ~/.kube"
                            sh "echo ${K8S_CONFIG} | base64 -d > ~/.kube/config"

                            sh "sed -i 's|{image}|${IMAGE_NAME}|g' kubernetes.yml"
                            sh "sed -i 's|{namespaces}|${params.BUILD_BRANCH}|g' kubernetes.yml"

                            sh "kubectl apply -f kubernetes.yml --namespace=${params.BUILD_BRANCH}"
                        }
                    }
                }
            }

            stage("邮件通知") {
                steps {
                    notify('gaowei@fengjinggroup.com')
                }
            }
        }

        post {
            always {cleanWs()}

            success {
                dingding(true)
            }

            failure {
                dingding(false)
            }
        }

    }
}