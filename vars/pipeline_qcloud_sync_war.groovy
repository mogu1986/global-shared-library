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
            identity = credentials("ssh-49.232.229.62")
        }

        parameters {
            string(name: 'app', defaultValue: '', description: '输入app:')
        }

        stages {

            stage('获取APP信息') {
                steps {
                    script {
                        def appMeta = new com.sxh.AppMeta().getMeta("${params.app}")
                        log.debug("${params.app} 查找结果 = ${appMeta}")

                        env.APP = "${appMeta.app}"
                        env.PROJECT_NAME = "${env.APP}"
                        env.GIT = "${appMeta.git}"
                        env.ARTIFACT = "${appMeta.artifact}"

                        def flag = appMeta.get('group').startsWith("jfb_ec")
                        if (flag) {
                            log.debug("jfb_ec 开头")
                            env.PROJECT_NAME = "jfb_ec_${env.APP}"
                        }
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
                            sh "git clone -b qcloud http://gitlab.shixhlocal.com/devops/config.git"
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

            stage('同步腾讯云') {
                steps {
                    script {
                        def remote = [:]
                        remote.name = 'root'
                        remote.host = "49.232.229.62"
                        remote.port = 62222
                        remote.user = 'root'
                        remote.identityFile = "${identity}"
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
