def call() {
    def flag = "${params.BUILD_ENV}" == 'sit'
    log.debug("is sit_envt ${flag}")
    return flag
}