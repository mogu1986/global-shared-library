def call(Map params) {
    def map = [:]
    map = ['distribution':'2632d5e4376ad7aa300fd70e3b8254504573a294']

    def lang = params.get('LANG')
    def app = params.get('APP_NAME')

    def key = ""
    def path = ""

    if (params.containsKey('WAR_PATH')) {
        key = "WAR_PATH"
        path = params.get('WAR_PATH')
    } else if (params.containsKey('HTML_PATH')) {
        key = "HTML_PATH"
        path = params.get(key)
    }

    log.debug("lang = ${lang}, app = ${app}, ${key} = ${path}")

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