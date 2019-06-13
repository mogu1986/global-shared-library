def call(String lang, String projectKey) {
    log.debug("lang: ${lang}, projectKey: ${projectKey}")

    def map = [:]
    map = ['distribution':'2632d5e4376ad7aa300fd70e3b8254504573a294']

    switch (lang) {
        case "node":
            nodePipeline()
            break;
        case "java":
            javaPipeline(projectKey, map.get(projectKey))
            break;
        default:
            println "nice to meet you"
    }
}