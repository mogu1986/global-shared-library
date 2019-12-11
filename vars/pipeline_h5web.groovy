/*
*  Description: jfb_ec_wx 定制的 pipeline，需要先打wx-web，然后把文件放入webapps下面
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
            timeout(time: 30, unit: 'MINUTES')
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
            choice(name: 'BUILD_BRANCH', choices: 'dev\nrelease', description: '请选择分支:')
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

            stage('wx') {
                steps {
                    script {
                        log.debug("选择的分支: ${params.BUILD_BRANCH}")
                        log.debug("部署环境: ${params.BUILD_ENV}")
                        env.PROJECT_NAME = "jfb_ec_wx"
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

            stage('h5-wechat') {
                steps {
                    script {
                        sh "git clone -b ${params.BUILD_BRANCH} http://gitlab.shixhlocal.com/bizplatform/h5-wechat.git"
                    }
                }
            }

            stage('编译h5-wechat') {
                steps {
                    script {
                        dir('h5-wechat') {
                            nodejs('NODEJS') {
                                sh "cnpm install"
                                sh "cnpm run wxsh-${params.BUILD_ENV}"
                            }
                        }
                    }
                }
            }

            stage('嵌入wx') {
                steps {
                    script {
                        echo "开始覆盖"
                        sh 'ls -lh'
                        sh 'cp -rp h5-wechat/dist /tmp/'
                        sh 'rm -rf jfb_ec_wx/src/main/webapp/css'
                        sh 'rm -rf jfb_ec_wx/src/main/webapp/js'
                        sh 'rm -rf jfb_ec_wx/src/main/webapp/image'
                        sh 'cp -rp h5-wechat/dist/wxsh/css jfb_ec_wx/src/main/webapp/'
                        sh 'cp -rp h5-wechat/dist/wxsh/js jfb_ec_wx/src/main/webapp/'
                        sh 'cp -rp h5-wechat/dist/wxsh/image jfb_ec_wx/src/main/webapp/'
                        sh 'cp -rp h5-wechat/dist/wxsh/* jfb_ec_wx/src/main/webapp/WEB-INF/webpage/'
                    }
                }
            }

            stage('编译wx') {
                steps {
                    script {
                        configFileProvider([configFile(fileId: "maven-global-settings", variable: 'MAVEN_SETTINGS_ENV')]) {
                            sh "mvn -s ${MAVEN_SETTINGS_ENV} clean package -Pwx -B -Dfile.encoding=UTF-8 -Dmaven.test.skip=true -U"
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
                                    playbook: "playbook_tomcat.yml",
                                    inventory: "hosts/${params.BUILD_ENV}.ini",
                                    hostKeyChecking: false,
                                    colorized: true,
                                    extraVars: [
                                        lang    : "tomcat",
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
                            url: "${env.JENKINS_URL}/view/PRD/job/aliyun-sync-war-h5web/buildWithParameters?token=${env.PORTAL_TOKEN}&app=${env.APP}",
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
                            url: "${env.JENKINS_URL}/view/PRD/job/qcloud-sync-war-h5web/buildWithParameters?token=${env.PORTAL_TOKEN}&app=${env.APP}",
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
