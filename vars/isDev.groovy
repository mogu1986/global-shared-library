def call() {
    def flag = "${params.BUILD_BRANCH}" == 'dev'
    log.debug("is dev ${flag}")
    return flag
}