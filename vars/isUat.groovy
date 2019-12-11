def call() {
    def flag = "${params.BUILD_ENV}" == 'uat'
    log.debug("is uat ${flag}")
    return flag
}