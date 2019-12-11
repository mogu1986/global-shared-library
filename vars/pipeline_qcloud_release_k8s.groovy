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
            identity = credentials("ssh-49.232.229.62")
        }

        parameters {
            string(name: 'json', defaultValue: '', description: '输入json:')
        }

        stages {

            stage('认证') {
                steps {
                    timeout(time: 1, unit: 'MINUTES') {
                        input (
                            message: "即将发布到腾讯云 Kubernetes 应用，发布或者停止",
                            ok: "确定",
                            submitter: "chengmangmang@shixh.com,yangyin@shixh.com,gaowei@shixh.com"
                        )
                    }
                }
            }

            stage('清空目录') {
                steps {
                    deleteDir()
                }
            }

            stage('发布') {
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

                        def cmd = new StringBuffer("")

                        def rs = new com.sxh.AppMeta().getStr("${params.json}")
                        rs.each { e ->
                            def ns = new com.sxh.AppMeta().getNamespace("${e.key}")
                            log.debug("namespace = " + "${ns} , " + e.key + "," + e.value)
                            cmd.append("kubectl -n ${ns} set image deployment ${e.key} ${e.key}=ccr.ccs.tencentyun.com/sxhharbor/${e.key}:${e.value} && ")
                        }

                        cmd.delete(cmd.length() - 3, cmd.length())

                        log.debug("${cmd}")

                        writeFile file: 'abc.sh', text: "${cmd}"
                        sshScript remote: remote, script: "abc.sh"

                        sshCommand remote: remote, command: "kubectl get deploy --all-namespaces"

//                        kubectl -n crm set image deployment ${params.app} ${params.app}=ccr.ccs.tencentyun.com/sxhharbor/${params.app}:${params.version}
                    }
                }
            }

        }

    }
}
