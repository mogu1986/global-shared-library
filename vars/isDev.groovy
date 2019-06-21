def call() {
    def flag = "${params.BUILD_BRANCH}" == 'dev'
    return flag
}