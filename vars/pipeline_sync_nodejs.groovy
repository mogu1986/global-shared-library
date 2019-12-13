/*
*  Description: java pipeline as code
*  sh "npm install -g cnpm --registry=https://registry.npm.taobao.org"
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

        parameters {
            string(name: 'app', defaultValue: 'portal-ui', description: '输入app:')
        }

        stages {

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
                        sh "mv sxh_config/${params.app}/nginx.conf ./"
                        sh "rm -rf sxh_config"
                        sh "ls -lh"
                    }
                }
            }

            stage('编译') {
                steps {
                    nodejs('NODEJS') {
                        sh "npm install --registry=http://10.50.4.3:4873 sxh-vue-common --save"
                        sh "cnpm install --unsafe-perm"
                        sh "npm run build"
                        sh "ls -lh"
                    }
                }
            }

            stage('推送镜像') {
                steps {
                    script {

                        env.IMAGE_NAME = "${HARBOR}/library/${params.app}:${BUILD_ID}"

                        docker.withRegistry("$HARBOR_URL", "harbor") {
                            configFileProvider([configFile(fileId: 'dockerfile_nodejs', variable: 'DOCKER_FILE')]) {
                                def app = docker.build("${env.IMAGE_NAME}", "--no-cache -f ${DOCKER_FILE} .")
                                app.push()
                            }
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

        post {
            always {cleanWs()}
        }

    }
}