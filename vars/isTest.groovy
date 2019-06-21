def call() {
    def flag = "${params.BUILD_BRANCH}" == 'test'
    log.debug('is test ${flag}')
    return flag
}