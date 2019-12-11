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
        }

        parameters {
            string(name: 'app', defaultValue: '', description: '输入app:')
            string(name: 'imageId', defaultValue: '', description: '输入镜像ID:')
        }

        stages {

            stage('同步阿里云') {
                steps {
                    script {
                        log.debug("选择的应用 = ${params.app}")
                        log.debug("镜像ID = ${params.imageId}")
                        if ("${params.imageId}" == "") {
                            throw new GroovyRuntimeException("imageId 不能为空")
                        }
                        env.IMAGE_NAME = "${HARBOR}/library/${params.app}:${params.imageId}"
                        env.ALIYUN_IMAGE_NAME = "registry.cn-beijing.aliyuncs.com/sxhharbor/${params.app}:${params.imageId}"

                        log.debug("镜像ID = ${env.IMAGE_NAME}")
                        log.debug("阿里云镜像ID = ${env.ALIYUN_IMAGE_NAME}")


                        docker.withRegistry("https://registry.cn-beijing.aliyuncs.com", "harbor_aliyun") {
                            sh "docker pull $IMAGE_NAME"
                            sh "docker tag $IMAGE_NAME $ALIYUN_IMAGE_NAME"
                            sh "docker push $ALIYUN_IMAGE_NAME"
                        }
                    }
                }
            }


            stage('同步腾讯云') {
                steps {
                    script {
                        env.QCLOUD_IMAGE_NAME = "ccr.ccs.tencentyun.com/sxhharbor/${params.app}:${params.imageId}"

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

        post {
            always {cleanWs()}
        }

    }
}
