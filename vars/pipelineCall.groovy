def call(Map params) {

    if (params == null || params.size() == 0) {
        log.error("params is null")
        return
    }

    def keys = ['lang', 'app', 'artifact']

    for (int i = 0; i < keys.size(); i++) {
        def key = keys.get(i)
        if (!params.containsKey(key)) {
            log.error("key ${key} is null")
            return
        }
    }

    def lang = params.get('lang')
    def app = params.get('app')
    def artifact = params.get('artifact')

    log.debug("lang = ${lang}, app = ${app}, artifact = ${artifact}")

    putSonarKey(params, app)

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

def putSonarKey(Map params, String app) {

    def sonar = [:]
    sonar.put('demo', '2632d5e4376ad7aa300fd70e3b8254504573a294')
    sonar.put('vue', '2632d5e4376ad7aa300fd70e3b8254504573a294')
    sonar.put('distribution', '9fd1c8b5372f57a5487355c43b91ad956a8cd25c')

    if (sonar.containsKey(app)) {
        def key = sonar.get(app)
        params.put('sonar_login', key)
        log.debug("sonar_login = ${key}")
    }

}