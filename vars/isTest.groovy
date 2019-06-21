def call() {
    def flag = "${params.BUILD_BRANCH}" == 'test'
    log.debug("is test_envt ${flag}")
    return flag
}