def call(Map params) {

    def keys = ['lang', 'app', 'artifact', 'abc']

    for (int i = 0; i < keys.size(); i++) {
        def key = keys.get(i)
        if (!params.containsKey(key)) {
            log.debug("key ${key} is null")
            return
        }
    }

    def lang = params.get('lang')
    def app = params.get('app')
    def artifact = params.get('artifact')

    log.debug("lang = ${lang}, app = ${app}, artifact = ${artifact}")

    def key = getSonarKey(app)
    log.debug("key = ${key}")

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

def getSonarKey(String app) {

    def map = [:]
    map.put('demo', '2632d5e4376ad7aa300fd70e3b8254504573a294')
    map.put('vue', '2632d5e4376ad7aa300fd70e3b8254504573a294')
    map.put('distribution', '2632d5e4376ad7aa300fd70e3b8254504573a294')

    def key = map.get(app)

    return key
}