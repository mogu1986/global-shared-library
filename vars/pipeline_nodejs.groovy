/*
*  Description: java pipeline as code
*  Date: 2019-04-23 16:14
*  Author: gaowei
*/
def call(Map map) {
    pipeline {

        agent {
            label 'nodejs'
        }
//
//        triggers {
//            cron('H/5 * * * *')
//        }

//        triggers { upstream(upstreamProjects: 'jenkins-demo', threshold: hudson.model.Result.SUCCESS) }

        options {
            buildDiscarder(logRotator(numToKeepStr: '50'))
            disableConcurrentBuilds()
            timeout(time: 10, unit: 'MINUTES')
            timestamps()
        }

        environment {
            APP = "${map.app}"
            LANG = "${map.lang}"
            SONAR_SOURCES = "${map['sonar.sources']}"
            SONAR_LOGIN = credentials("${APP}-sonar-login")
            TAR_NAME = "dist.tar.gz"

            TEST_DEPLOY_PWD = credentials("TEST_DEPLOY_PWD")
            UAT_DEPLOY_PWD = credentials("UAT_DEPLOY_PWD")
        }

        parameters {
            choice(name: 'BUILD_BRANCH', choices: 'dev\ntest', description: '请选择部署的环境')
            string(name: 'ARTIFACT', defaultValue: "${map.artifact}", description: 'yarn build生成的包路径，相对于workspace')
        }

        stages {

            stage('env') {
                steps {
                    script {
                        withSonarQubeEnv('sonar'){
                            sh 'printenv'
                            log.debug("${SONAR_HOST_URL}")
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
                    timeout(time: 1, unit: 'MINUTES') {
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
                                    message: "即将发布到${env_text}环境，请输入密钥：",
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
            }

            stage('编译') {
                steps {
                    nodejs(nodeJSInstallationName: 'NODEJS') {
                        sh "yarn install"
                        sh "yarn build"
                    }
                }
            }

            stage('打包') {
                steps {
                    dir("${params.ARTIFACT}"){
                        sh "tar -zcvf ${TAR_NAME} *"
                    }
                }
            }

            stage('Sonar分析') {
                steps {
                    sonar { sonarHome ->
                        sh "${sonarHome}/bin/sonar-scanner -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=${SONAR_LOGIN} -Dsonar.projectKey=${env.APP} -Dsonar.projectName=${env.APP} -Dsonar.sources=${env.SONAR_SOURCES}"
                    }
                }
            }

            stage("Ansible部署"){
                steps{
                    script{
                        docker.image('harbor.top.mw/library/ansible:centos7').inside() {
                            checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false,
                                      extensions: [], submoduleCfg: [],
                                      userRemoteConfigs: [[credentialsId: 'gitlab', url: 'https://github.com/mogu1986/jenkins-ansible-playbooks.git']]])
                            ansiColor('xterm') {
                                ansiblePlaybook(
                                        playbook: "playbook_${env.LANG}.yml",
                                        inventory: "hosts/${params.BUILD_BRANCH}.ini",
                                        hostKeyChecking: false,
                                        colorized: true,
                                        extraVars: [
                                            lang: "${env.LANG}",
                                            app: [value: "${env.APP}", hidden: false],
                                            artifact: "${env.WORKSPACE}/${params.ARTIFACT}/${TAR_NAME}"
                                        ]
                                )
                            }
                        }
                    }
                }
            }

            stage('钉钉通知') {
                steps{
                    script{
                        def response = httpRequest "http://www.baidu.com"
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