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

    if (!params.containsKey('sonar_sources')) {
        log.debug("sonar : set sonar_sources default value")
        params.put('sonar_sources', '.')
    }

    log.debug("sonar : put ${app} sonar_login")

    def sonar = [:]
    sonar.put('demo', '2632d5e4376ad7aa300fd70e3b8254504573a294')
    sonar.put('vue', 'd888bbc3a281b67b3dfeac62f790e86dfb4072b0')
    sonar.put('distribution', '9fd1c8b5372f57a5487355c43b91ad956a8cd25c')

    if (sonar.containsKey(app)) {
        def key = sonar.get(app)
        params.put('sonar_login', key)
    }

}