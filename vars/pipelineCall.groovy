def call(Map params) {

    if (params == null || params.size() == 0) {
        log.error("error : params is null")
        return
    }

    def keys = ['lang', 'app', 'artifact']

    for (int i = 0; i < keys.size(); i++) {
        def key = keys.get(i)
        if (!params.containsKey(key)) {
            log.error("error : key ${key} is null")
            return
        }
    }

    def lang = params.get('lang')
    def app = params.get('app')
    def artifact = params.get('artifact')

    log.debug("print : lang = ${lang}, app = ${app}, artifact = ${artifact}")

    putSonar(params)

    switch (lang) {
        case "java":
            pipeline_java(params)
            break;
        case "tomcat":
            pipeline_tomcat(params)
            break;
        case "nodejs":
            pipeline_nodejs(params)
            break;
        default:
            println "nice to meet you"
    }

}

def putSonar(Map params) {

    def keys = ['sonar_sources', 'sonar_java_binaries']

    for (int i = 0; i < keys.size(); i++) {
        def key = keys.get(i)
        if (!params.containsKey(key)) {
            params.put(key, '.')
        } else {
            log.debug("包含: ${key}")
        }
    }
//
//    ["sonar_sources", "sonar_java_binaries"].each{
//        def key = "${it}"
//        if (!params.containsKey(key)) {
//            log.debug("sonar : set ${key} .")
//            params.put(key, '.')
//        }
//    }
}