/*
*  Description: java pipeline as code
*  Date: 2019-04-23 16:14
*  Author: gaowei
*/
def call(Map map) {
    pipeline {

        agent {
            label 'nodejs'
        }
//
//        triggers {
//            cron('H/5 * * * *')
//        }

        triggers { upstream(upstreamProjects: 'jenkins-demo', threshold: hudson.model.Result.SUCCESS) }

        options {
            buildDiscarder(logRotator(numToKeepStr: '50'))
            disableConcurrentBuilds()
            timeout(time: 10, unit: 'MINUTES')
        }

        environment {
            HARBOR = "harbor.top.mw"
            HARBOR_URL = "http://${HARBOR}"

            // 容器相关配置
            IMAGE_NAME = "${HARBOR}/library/${JOB_NAME}:${BUILD_ID}"

            APP = "${map.app}"
            LANG = "${map.lang}"
        }

        parameters {
            choice(name: 'BUILD_BRANCH', choices: 'dev\ntest', description: '请选择部署的环境')
            string(name: 'ARTIFACT', defaultValue: "${map.artifact}", description: 'yarn build生成的包路径，相对于workspace')
        }

        tools {
            nodejs 'NODEJS'
        }

        stages {

            stage('编译') {
                steps {
                    sh "yarn install"
                    sh "yarn build"
                    sh 'ls -la'
                    zip zipFile: 'Test.zip', dir:"${env.WORKSPACE}/${params.ARTIFACT}"
                }
            }

            stage('Sonar分析') {
                steps {
                    script {
                        def sonarHome = tool name: 'SonarQube Scanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
                        wrap([$class: 'MaskPasswordsBuildWrapper', varPasswordPairs:[
                                [password: "${map.sonar_login}", var: 's1']
                        ]]) {
                            sh "${sonarHome}/bin/sonar-scanner -Dsonar.host.url=http://sonar.top.mw -Dsonar.login=${map.sonar_login} -Dsonar.projectKey=${map.app} -Dsonar.sources=${map.sonar_sources}"
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
                                                app: [value: "${env.APP}", hidden: false],
                                                artifact: "${env.WORKSPACE}/${params.ARTIFACT}/"
                                        ]
                                )
                            }
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