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
            timeout(time: 30, unit: 'MINUTES')
        }

        environment {
            APP = "${map.app}"
            LANG = "${map.lang}"

            PORTAL_TOKEN = credentials("portal")

            IMAGE_NAME = "${HARBOR}/library/${JOB_NAME}:${BUILD_ID}"
        }

        parameters {
            choice(name: 'BUILD_BRANCH', choices: 'dev\nrelease', description: '请选择分支:')
            choice(name: 'BUILD_ENV', choices: 'mit\nsit\nuat', description: '请选择部署环境:')
        }

        stages {

            stage('输入密钥') {
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
                        git branch: params.BUILD_BRANCH, credentialsId: 'gitlab', url: map.git
                    }
                }
            }

            stage('拉取配置') {
                steps {
                    script {
                        sh "git clone -b ${params.BUILD_ENV} http://gitlab.shixhlocal.com/devops/config.git sxh_config"
                        sh "mv sxh_config/${env.APP}/Dockerfile ./"
                        sh "mv sxh_config/${env.APP}/nginx.conf ./"
                        sh "rm -rf sxh_config"
                        sh "ls -lh"
                    }
                }
            }

            stage('推送镜像') {
                steps {
                    script {
                        docker.withRegistry("$HARBOR_URL", "harbor") {
                            def app = docker.build("$IMAGE_NAME", "--no-cache .")
                            app.push()
                        }
                        sh "docker rmi -f $IMAGE_NAME"
                    }
                }
            }

            stage("Ansible部署"){
                steps{
                    script{
                        docker.image('harbor.shixhlocal.com/library/ansible:centos7').inside() {
                            checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false,
                                      extensions: [], submoduleCfg: [],
                                      userRemoteConfigs: [[credentialsId: 'gitlab', url: 'http://gitlab.shixhlocal.com/devops/jenkins-ansible-playbooks.git']]])
                            ansiColor('xterm') {
                                ansiblePlaybook(
                                    playbook: "playbook_nodejs.yml",
                                    inventory: "hosts/${params.BUILD_ENV}.ini",
                                    hostKeyChecking: false,
                                    colorized: true,
                                    extraVars: [
                                        lang: "nodejs",
                                        app: [value: "${env.APP}", hidden: false],
                                        env: [value: "${params.BUILD_ENV}", hidden: false],
                                        portArgs: "${map.portArgs}",
                                        run: "${map.run}",
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
                        def url = "${env.JENKINS_URL}/view/PRD/job/aliyun-harbor-html/buildWithParameters?token=${env.PORTAL_TOKEN}&app=${env.APP}"
                        log.debug(" url = ${url}")

                        def response = httpRequest(
                                url: "${url}",
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