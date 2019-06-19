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
        }

        environment {
            APP = "${map.app}"
            LANG = "${map.lang}"
            SONAR_SOURCES = "${map['sonar.sources']}"
            SONAR_LOGIN = credentials("${APP}-sonar-login")

            // 容器相关配置
            IMAGE_NAME = "${HARBOR}/library/${JOB_NAME}:${BUILD_ID}"
        }

        parameters {
            choice(name: 'BUILD_BRANCH', choices: 'dev\ntest', description: '请选择部署的环境')
            string(name: 'ARTIFACT', defaultValue: "${map.artifact}", description: 'yarn build生成的包路径，相对于workspace')
        }

        tools {
            nodejs 'NODEJS'
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
                    expression { return params.BUILD_BRANCH == 'dev'}
                }
                steps {
                    script {
                        inputParam = input (
                                message: "即将发布到测试环境，请输入密钥!",
                                ok: "确定",
                                submitter: "admin,gaowei",
                                parameters: [
                                        password(name: 'DEPLOY_PWD', defaultValue: '', description: '')
                                ]
                        )
                        log.debug("${inputParam}")
                        if ("${inputParam}" == "${env.TEST_DEPLOY_PWD}") {
                            log.debug("YES YES")
                        } else {
                            log.error('密码错误')
                            throw new GroovyRuntimeException('密码错误')
                        }
                    }
                }
            }

            stage('编译') {
                steps {
                    sh "yarn install"
                    sh "yarn build"
                    sh 'ls -la'
                }
            }

            stage('打zip包') {
                steps {
                    script {
//                        zip zipFile: 'Test.zip', dir: "dist", glob: ''
                        sh 'zip -r dist.zip dist'
                        sh 'ls -la'
                    }
                }
            }

            stage('Sonar分析') {
                steps {
                    script {
                        def sonarHome = tool name: 'SonarQube Scanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
                        withSonarQubeEnv('sonar'){
                            sh "${sonarHome}/bin/sonar-scanner -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=${SONAR_LOGIN} -Dsonar.projectKey=${env.APP} -Dsonar.projectName=${env.APP} -Dsonar.sources=${env.SONAR_SOURCES}"
                        }
                    }
                }
            }

            stage("ansible自动化部署"){
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
                                            artifact: "${env.WORKSPACE}/${params.ARTIFACT}/"
                                        ]
                                )
                            }
                        }
                    }
                }
            }

        }

        post {
            always {cleanWs()}
        }
    }
}