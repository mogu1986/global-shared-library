def call() {
    def flag = "${params.BUILD_ENV}" == 'mit'
    log.debug("is mit dev ${flag}")
    return flag
}