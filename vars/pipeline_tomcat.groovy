/*
*  Description: java pipeline as code
*  Date: 2019-04-23 16:14
*  Author: wei.gao
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

        options {
            buildDiscarder(logRotator(numToKeepStr: '50'))
            disableConcurrentBuilds()
            timeout(time: 10, unit: 'MINUTES')
        }

        environment {
            APP = "${map.app}"
            LANG = "${map.lang}"
            SONAR_SOURCES = "${map['sonar.sources']}"
            SONAR_JAVA_BINARIES = "${map['sonar.java.binaries']}"
            SONAR_LOGIN = credentials("${APP}-sonar-login")
        }

        parameters {
            choice(name: 'BUILD_BRANCH', choices: 'dev\ntest', description: '请选择部署的环境')
            string(name: 'ARTIFACT', defaultValue: "${map.artifact}", description: 'war包路径，相对于workspace')
        }

        stages {

            stage('env') {
                steps {
                    script {
                        withSonarQubeEnv('sonar'){
                            sh 'printenv'
                            log.debug("${SONAR_HOST_URL}")
                            log.debug("${SONAR_LOGIN}")
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
                        input (
                            message: "即将发布到测试环境，请输入密钥!",
                            ok: "确定",
                            submitter: "admin,gaowei",
                            parameters: {
                                password(name: 'DEPLOY_PWD', defaultValue: '', description: '')
                            }
                        )
                        sh "${DEPLOY_PWD}"
                        if ("${DEPLOY_PWD}" == 'gaowei') {
                            echo "YES YES"
                            echo "${TEST_DEPLOY_PWD}"
                        } else {
                            log.error('密码错误')
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
                    configFileProvider(
                            [configFile(fileId: "${env.BUILD_BRANCH}-maven-global-settings", variable: 'MAVEN_SETTINGS')]) {
                        script {
                            docker.image('maven:3-jdk-8-alpine').inside('-v /root/.m2:/root/.m2 -v /root/.sonar:/root/.sonar') {
                                sh "mvn -s $MAVEN_SETTINGS clean deploy -B -Dfile.encoding=UTF-8 -Dmaven.test.skip=true -U"
                            }
                        }
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
                                            artifact: "${env.WORKSPACE}/${params.ARTIFACT}"
                                        ]
                                )
                            }
                        }
                    }
                }
            }

            stage("邮件通知") {
                steps {
                    configFileProvider(
                            [configFile(fileId: "html-global-settings", variable: 'body')]) {
                        emailext(
                                to: 'gaowei@fengjinggroup.com',
                                subject: "Running Pipeline: ${currentBuild.fullDisplayName}",
                                body: readFile("${body}")
                        )
                    }
                }
            }

        }

        post {
            always {cleanWs()}
        }

    }
}