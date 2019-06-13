def call() {

    pipeline {

        agent {
            label 'master'
        }

        options {
            // 保留1个工程
            buildDiscarder(logRotator(numToKeepStr: '50'))
            // 不允许同时执行多次
            disableConcurrentBuilds()
            // 整个pipeline超时时间
            timeout(time: 20, unit: 'MINUTES')
        }

        environment {
            // harbor 相关配置
            HARBOR = "harbor.top.mw"
            HARBOR_URL = "http://${HARBOR}"
            HARBOR_CRED = credentials('harbor')

            // 容器相关配置
            IMAGE_NAME = "${HARBOR}/library/${JOB_NAME}:${BUILD_ID}"
            K8S_CONFIG = credentials('k8s-config')
        }

        parameters {
            choice(name: 'BUILD_BRANCH', choices: 'dev\ntest', description: '请选择部署的环境')
            choice(name: 'BUILD_BRANCH_LOCAL', choices: 'test\nuat', description: '请选择部署环境所需要的local文件')
        }

        tools {
            nodejs 'NODEJS'
        }

        stages {

            stage('echo') {
                steps {
                    echo '你的选择:'
                    echo "部署的环境: ${params.BUILD_BRANCH}"
                    echo "部署 ${params.BUILD_BRANCH}环境，用的 .env.${params.BUILD_BRANCH_LOCAL} 文件 "
                }
            }

            stage('checkout') {
                steps { git branch: params.BUILD_BRANCH, credentialsId: env.GIT_SIGN, url: GIT_URL }
            }

            stage('compile') {
                steps {
                    sh "yarn install"
                    sh "yarn run build --mode ${params.BUILD_BRANCH_LOCAL}"
                }
            }

            stage('push harbor') {
                steps {
                    script {
                        docker.withRegistry("$HARBOR_URL", "harbor") {
                            def app = docker.build("$IMAGE_NAME")
                            app.push()
                            app.push('latest')
                        }
                        sh "docker rmi -f $IMAGE_NAME"
                    }
                }
            }

            stage('deploy') {
                steps {
                    sh "sed -i \"s|#IMAGE|${IMAGE_NAME}|g\" kubernetes.yml"
                    sh "kubectl apply -f kubernetes.yml --namespace=${params.BUILD_BRANCH}"
                }
            }

        }

        post {
            always {cleanWs()}
        }
    }

}