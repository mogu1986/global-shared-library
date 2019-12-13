/*
*  Description: java pipeline as code
*  Date: 2019-04-23 16:14
*  Author: wei.gao
*/
def call(Map map) {

    pipeline {

        agent {
            label 'swarm'
        }

        options {
            buildDiscarder(logRotator(numToKeepStr: '50'))
            disableConcurrentBuilds()
            timeout(time: 10, unit: 'MINUTES')
        }

        tools {
            maven 'maven'
        }

        environment {
            APP = "${map.app}"
            LANG = "${map.lang}"
            APP_PORT = "${map.appPort}"
            GROUP= "${map.group}"
            ARTIFACT = "${map.artifact}"

            PORTAL_TOKEN = credentials("portal")
        }

        parameters {
            gitParameter(branchFilter: 'origin/(.*)', defaultValue: 'dev', name: 'BUILD_BRANCH', type: 'PT_BRANCH', description: '请选择分支:', useRepository: "${map.git}")
            choice(name: 'BUILD_ENV', choices: 'mit\nsit\nuat', description: '请选择部署环境:')
        }

        stages {

            stage('认证') {
                when {
                    expression {
                        return !isDev()
                    }
                }
                steps {
                    auth()
                }
            }

            stage('清空目录') {
                steps {
                    deleteDir()
                }
            }

            stage('拉取代码') {
                steps {
                    script {
                        log.debug("选择的分支: ${params.BUILD_BRANCH}")
                        log.debug("部署环境: ${params.BUILD_ENV}")

                        env.PROJECT_NAME = "${env.APP}"

                        def flag = map.get('group').startsWith("jfb_ec")
                        if (flag) {
                            log.debug("jfb_ec 开头")
                            env.PROJECT_NAME = "jfb_ec_${env.APP}"
                        }

                        log.debug("project.name = ${env.PROJECT_NAME}")
                        git branch: params.BUILD_BRANCH, credentialsId: 'gitlab', url: map.git
                    }
                }
            }

            stage('替换配置') {
                steps {
                    dir("${env.PROJECT_NAME}") {
                        script {
                            sh "git clone -b ${params.BUILD_ENV} http://gitlab.shixhlocal.com/devops/config.git"
                            sh "ls -lh"
                            sh "cp -rp config/${env.APP}/config/* src/main/resources/config/"
                            sh "cp -rp config/${env.APP}/properties/* src/main/resources/properties/"
                        }
                    }
                }
            }

            stage('编译打包') {
                steps {
                    script {
                        configFileProvider([configFile(fileId: "maven-global-settings", variable: 'MAVEN_SETTINGS')]) {
                            sh "mvn -s $MAVEN_SETTINGS clean package -B -Dfile.encoding=UTF-8 -P${env.APP} -Dmaven.test.skip=true -U"
                        }
                    }
                }
            }

            stage("Ansible部署") {
                steps{
                    script {
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
                                        lang    : "${env.LANG}",
                                        app     : [value: "${env.APP}", hidden: false],
                                        appPort : [value: "${env.APP_PORT}", hidden: false],
                                        env     : [value: "${params.BUILD_ENV}", hidden: false],
                                        artifact: "${env.WORKSPACE}/${env.PROJECT_NAME}/${env.ARTIFACT}"
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
                            url: "${env.JENKINS_URL}/view/PRD/job/aliyun-sync-war/buildWithParameters?token=${env.PORTAL_TOKEN}&app=${env.APP}",
                            httpMode: 'GET'
                        )
                        println('Status: '+response.status)
                        println('Response: '+response.content)
                    }
                }
            }

            stage('同步腾讯云') {
                when {
                    expression {
                        return isUat()
                    }
                }
                steps {
                    script {
                        def response = httpRequest(
                            url: "${env.JENKINS_URL}/view/PRD/job/qcloud-sync-war/buildWithParameters?token=${env.PORTAL_TOKEN}&app=${env.APP}",
                            httpMode: 'GET'
                        )
                        println('Status: '+response.status)
                        println('Response: '+response.content)
                    }
                }
            }

        }

    }
}