def call() {

    pipeline {

        agent any

        environment {
            _harbor = "harbor.top.mw"
            _harborRegistory = "http://${_harbor}"
            _image = "${_harbor}/library/${JOB_NAME}:${BUILD_ID}"

            _harbor_cre = credentials('harbor')
        }

        parameters {
            choice(name: 'BUILD_BRANCH', choices: 'dev\ntest', description: '请选择部署的环境')
        }

        stages {

            stage('echo') {
                steps {
                    echo "部署的环境: ${params.BUILD_BRANCH}"
                }
            }

            stage('checkout') {
                steps { git branch: params.BUILD_BRANCH, credentialsId: env.GIT_SIGN, url: GIT_URL }
            }

            stage('build image') {
                sh "docker build -t $DOCKER_IMAGE $WORKSPACE/."
            }

            stage('push harbor') {
                sh "docker login -u $harbor_user -p $env.HARBOR_PWD $harbor"
                sh "docker push $DOCKER_IMAGE"
                sh "docker logout $harbor"
                sh "docker rmi -f $DOCKER_IMAGE"
            }

            stage('deploy') {
                sh "sed -i \"s|#IMAGE|${DOCKER_IMAGE}|g\" $WORKSPACE/kubernetes.yml"
                sh "kubectl apply -f $WORKSPACE/kubernetes.yml --namespace=${params.BUILD_BRANCH}"
            }

        }

        post {
            always {cleanWs()}
        }
    }

}