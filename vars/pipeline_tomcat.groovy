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
            // 保留50个工程
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

            // 容器相关配置
            IMAGE_NAME = "${HARBOR}/library/${JOB_NAME}:${BUILD_ID}"
            K8S_CONFIG = credentials('k8s-config')

            APP_NAME = "${map.APP_NAME}"
            LANG = "${map.LANG}"
        }

        parameters {
            choice(name: 'BUILD_BRANCH', choices: 'dev\ntest', description: '请选择部署的环境')
            string(name: 'WAR_PATH', defaultValue: "${map.WAR_PATH}", description: 'war包路径，相对于workspace')
        }

        stages {

            stage('拉取代码') {
                steps { git branch: params.BUILD_BRANCH, credentialsId: 'gitlab', url: GIT_URL }
            }

            stage('编译') {
                steps {
                    configFileProvider(
                            [configFile(fileId: "${env.BUILD_BRANCH}-maven-global-settings", variable: 'MAVEN_SETTINGS')]) {
                        script {
                            docker.image('maven:3-jdk-8-alpine').inside('-v /root/.m2:/root/.m2 -v /root/.sonar:/root/.sonar') {
                                sh "mvn -s $MAVEN_SETTINGS clean deploy -B -Dfile.encoding=UTF-8 -Dmaven.test.skip=true -U"
                            }
                        }
                    }
                }
            }

            stage("ansible自动化部署"){
                steps{
                    script{
                        docker.image('harbor.top.mw/library/ansible:centos7').inside() {
                            checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false,
                                      extensions: [], submoduleCfg: [],
                                      userRemoteConfigs: [[credentialsId: 'gitlab', url: 'https://github.com/mogu1986/jenkins-ansible-playbooks.git']]])
                            ansiColor('xterm') {
                                ansiblePlaybook(
                                        playbook: "playbook_${env.LANG}.yml",
                                        inventory: "hosts/${params.BUILD_BRANCH}.ini",
                                        hostKeyChecking: false,
                                        colorized: true,
                                        extraVars: [
                                                lang: "${env.LANG}",
                                                app: [value: "${env.APP_NAME}", hidden: false],
                                                war_path: "${env.WORKSPACE}/${params.WAR_PATH}"
                                        ]
                                )
                            }
                        }
                    }
                }
            }

            stage("邮件通知") {
                steps {
                    configFileProvider(
                            [configFile(fileId: "html-global-settings", variable: 'body')]) {
                        emailext(
                                to: 'gaowei@fengjinggroup.com',
                                subject: "Running Pipeline: ${currentBuild.fullDisplayName}",
                                body: readFile("${body}")
                        )
                    }
                }
            }
        }

        post {
            always {cleanWs()}
        }

    }
}