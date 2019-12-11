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
            IMAGE_NAME = "${HARBOR}/library/website:${BUILD_ID}"
        }

        parameters {
            string(name: 'app', defaultValue: 'website', description: '输入app:')
        }

        stages {

            stage('清空目录') {
                steps {
                    deleteDir()
                }
            }

            stage('拉取代码') {
                steps {
                    script {
                        log.debug("同步app: ${params.app}")
                        log.debug("部署环境: prod")
                        def appMeta = new com.sxh.AppMeta().getMeta("${params.app}")
                        log.debug("appMeta : ${appMeta}")

                        git branch: 'release', credentialsId: 'gitlab', url: appMeta.git
                    }
                }
            }

            stage('拉取配置') {
                steps {
                    script {
                        sh "git clone -b prod http://gitlab.shixhlocal.com/devops/config.git sxh_config"
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
                    }
                }
            }


            stage('同步阿里云') {
                steps {
                    script {
                        env.ALIYUN_IMAGE_NAME = "registry.cn-beijing.aliyuncs.com/sxhharbor/${params.app}:${BUILD_ID}"

                        echo "debug: 阿里云镜像ID = ${env.ALIYUN_IMAGE_NAME}"

                        docker.withRegistry("https://registry.cn-beijing.aliyuncs.com", "harbor_aliyun") {
                            sh "docker tag $IMAGE_NAME $ALIYUN_IMAGE_NAME"
                            sh "docker push $ALIYUN_IMAGE_NAME"
                        }
                    }
                }
            }

            stage('同步腾讯云') {
                steps {
                    script {
                        env.QCLOUD_IMAGE_NAME = "ccr.ccs.tencentyun.com/sxhharbor/${params.app}:${BUILD_ID}"

                        log.debug("镜像ID = ${env.IMAGE_NAME}")
                        log.debug("腾讯云镜像ID = ${env.QCLOUD_IMAGE_NAME}")

                        docker.withRegistry("https://ccr.ccs.tencentyun.com/sxhharbor", "harbor_tencent") {
                            sh "docker tag $IMAGE_NAME $QCLOUD_IMAGE_NAME"
                            sh "docker push $QCLOUD_IMAGE_NAME"
                        }
                        sh "docker rmi -f $QCLOUD_IMAGE_NAME"
                        sh "docker rmi -f $ALIYUN_IMAGE_NAME"
                        sh "docker rmi -f $IMAGE_NAME"

                    }
                }
            }


        }
    }
}