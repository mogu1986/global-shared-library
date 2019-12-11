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
            ssh_ops01 = credentials("ssh-47.94.196.227")
        }

        parameters {
            string(name: 'app', defaultValue: 'h5-web', description: '输入app:')
        }

        stages {

            stage('获取APP信息') {
                steps {
                    script {
                        def appMeta = new com.sxh.AppMeta().getMeta("${params.app}")
                        log.debug("${params.app} 查找结果 = ${appMeta}")

                        env.APP = "${appMeta.app}"
                        env.GIT = "${appMeta.git}"
                        env.ARTIFACT = "${appMeta.artifact}"
                        env.PROJECT_NAME = "jfb_ec_wx"

                        log.debug("project.name = ${env.PROJECT_NAME}")
                    }
                }
            }

            stage('拉取代码') {
                steps {
                    script {
                        git branch: 'release', credentialsId: 'gitlab', url: "${env.GIT}"
                    }
                }
            }

            stage('替换配置') {
                steps {
                    dir("${env.PROJECT_NAME}") {
                        script {
                            sh "git clone -b prod http://gitlab.shixhlocal.com/devops/config.git"
                            sh "ls -lh"
                            sh "cp -rp config/${env.APP}/config/* src/main/resources/config/"
                            sh "cp -rp config/${env.APP}/properties/* src/main/resources/properties/"
                        }
                    }
                }
            }

            stage('wx-web') {
                steps {
                    script {
                        sh "git clone -b release http://gitlab.shixhlocal.com/bizplatform/h5-wechat.git"
                    }
                }
            }

            stage('编译wx-web') {
                steps {
                    script {
                        dir('h5-wechat') {
                            nodejs('NODEJS') {
                                sh "cnpm install"
                                sh "cnpm run wxsh-build"
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

            stage('编译打包') {
                steps {
                    script {
                        configFileProvider([configFile(fileId: "maven-global-settings", variable: 'MAVEN_SETTINGS')]) {
                            sh "mvn -s $MAVEN_SETTINGS clean package -B -Dfile.encoding=UTF-8 -Pwx -Dmaven.test.skip=true -U"
                        }
                    }
                }
            }

            stage('同步阿里云') {
                steps {
                    script {
                        def remote = [:]
                        remote.name = 'root'
                        remote.host = "${ssh_ops01_USR}"
                        remote.port = 62222
                        remote.user = 'root'
                        remote.password = "${ssh_ops01_PSW}"
                        remote.allowAnyHosts = true
                        remote.logLevel = 'INFO'

                        sshCommand remote: remote, command: "mkdir -p /data/local/prd_test/${env.APP}"
                        sshPut remote: remote, from: "${env.PROJECT_NAME}/${env.ARTIFACT}", into: "/data/local/prd_test/${env.APP}/ROOT.war"
                    }
                }
            }

        }

        post {
            always {cleanWs()}
        }

    }
}
