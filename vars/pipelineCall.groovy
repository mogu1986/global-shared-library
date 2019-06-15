def call(Map params) {
    def map = [:]
    map = ['distribution':'2632d5e4376ad7aa300fd70e3b8254504573a294']

    def lang = params.get('LANG')
    def app = params.get('APP_NAME')

    log.debug("lang = ${lang}, app = ${app}")
    if (map.containsKey('WAR_PATH')) {
        log.debug("war_path = ${WAR_PATH}")
    }
    if (map.containsKey('HTML_PATH')) {
        log.debug("html_path = ${HTML_PATH}")
    }

    switch (lang) {
        case "node":
            nodePipeline()
            break;
        case "java":
            pipeline_java(projectKey, map.get(projectKey))
            break;
        case "tomcat":
            pipeline_tomcat(params)
            break;
        case "nginx":
            pipeline_nginx(params)
            break;
        default:
            println "nice to meet you"
    }
}