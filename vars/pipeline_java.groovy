/*
*  Description: java pipeline as code
*  Date: 2019-04-23 16:14
*  Author: gaowei
*/
def call(Map map) {
    pipeline {

        agent {
            label 'master'
        }

        options {
            // 保留1个工程
            buildDiscarder(logRotator(numToKeepStr: '10'))
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
        }

        stages {
            stage('拉取代码') {
                steps { git branch: params.BUILD_BRANCH, credentialsId: 'gitlab', url: GIT_URL }
            }

            stage('编译') {
                steps {
                    mvn { settings ->
                        sh "mvn -s ${settings} clean deploy -B -Dfile.encoding=UTF-8 -Dmaven.test.skip=true -U"
                    }
                }
            }

            stage('Sonar分析') {
                steps {
                    script {
                        def sonarHome = tool name: 'SonarQube Scanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
                        wrap([$class: 'MaskPasswordsBuildWrapper', varPasswordPairs:[
                                [password: "${map.sonar_login}", var: 's1']
                        ]]) {
                            sh "${sonarHome}/bin/sonar-scanner -Dsonar.host.url=http://sonar.top.mw -Dsonar.login=${map.sonar_login} -Dsonar.projectKey=${map.app} -Dsonar.projectName=${map.app} -Dsonar.sources=${map.sonar_sources}"
                        }
                    }
                }
            }

            stage('单元测试') {
                steps {
                    echo "未开放"
                }
            }

            stage('自动化测试') {
                when {
                    expression { return params.BUILD_BRANCH == 'test'}
                }
                failFast true
                parallel {
                    stage('UI自动化测试') {
                        steps {
                            echo "并行一"
                        }
                    }
                    stage('性能自动化测试') {
                        steps {
                            echo "并行二"
                        }
                    }
                    stage('接口自动化测试') {
                        steps {
                            echo "In stage Nested 1 within Branch C"
                        }
                    }
                }
            }

            stage('推送镜像') {
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

            stage('发布k8s') {
                steps {
                    script {
                        docker.image('harbor.top.mw/library/kubectl:1.7.6').inside() {
                            sh "mkdir -p ~/.kube"
                            sh "echo ${K8S_CONFIG} | base64 -d > ~/.kube/config"

                            sh "sed -i 's|{image}|${IMAGE_NAME}|g' kubernetes.yml"
                            sh "sed -i 's|{namespaces}|${params.BUILD_BRANCH}|g' kubernetes.yml"

                            sh "kubectl apply -f kubernetes.yml --namespace=${params.BUILD_BRANCH}"
                        }
                    }
                }
            }

            stage("邮件通知") {
                steps {
                    notify('gaowei@fengjinggroup.com')
                }
            }
        }

        post {
            always {cleanWs()}
        }

    }
}