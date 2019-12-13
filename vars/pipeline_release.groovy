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
            disableConcurrentBuilds()
            timeout(time: 10, unit: 'MINUTES')
        }

        environment {
            TAR_NAME = "ansible.tar.gz"
            ssh_ops01 = credentials("ssh-47.94.196.227")
        }

        parameters {
            choice(name: 'app', choices: 'admin\nh5-web', description: '请选择应用:')
        }

        stages {

            stage('认证') {
                steps {
                    timeout(time: 1, unit: 'MINUTES') {
                        input (
                            message: "即将发布到 ${params.app} 应用，发布或者停止",
                            ok: "确定",
                            submitter: "gaowei@shixh.com"
                        )
                    }
                }
            }

            stage('清空目录') {
                steps {
                    deleteDir()
                }
            }

            stage('ansible') {
                steps {
                    script {
                        sh "git clone -b master http://gitlab.shixhlocal.com/devops/jenkins-ansible-playbooks.git"
                        sh "rm -rf jenkins-ansible-playbooks/.git"
                        sh "tar -zcvf ${env.TAR_NAME} jenkins-ansible-playbooks"
                        sh "ls -lh"
                    }
                }
            }

            stage('发布') {
                steps {
                    script {
                        ansiColor('xterm') {
                            def remote = [:]
                            remote.name = 'root'
                            remote.host = "${ssh_ops01_USR}"
                            remote.port = 62222
                            remote.user = 'root'
                            remote.password = "${ssh_ops01_PSW}"
                            remote.allowAnyHosts = true
                            remote.logLevel = 'INFO'

                            def appMeta = new com.sxh.AppMeta().getMeta("${params.app}")
                            log.debug("${params.app} 查找结果 = ${appMeta}")
                            appMeta.lang = "tomcat"

                            def pushShell = "ansible-playbook playbook_${appMeta.lang}.yml -i hosts/prod.ini -e lang=${appMeta.lang} -e app=${params.app} -e appPort=8080 -e env=prod -e artifact=/data/local/prd_test/${params.app}/ROOT.war"

                            sshCommand remote: remote, command: "rm -rf /opt/ansible/*"
                            sshPut remote: remote, from: "${env.TAR_NAME}", into: "/opt/ansible"
                            sshCommand remote: remote, command: "cd /opt/ansible && tar -xzf ${env.TAR_NAME}"
                            sshCommand remote: remote, command: "cd /opt/ansible/jenkins-ansible-playbooks && ${pushShell}"
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
