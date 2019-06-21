def call() {
    def flag = "${params.BUILD_BRANCH}" == 'dev'
    log.debug("is dev_envd ${flag}")
    return flag
}