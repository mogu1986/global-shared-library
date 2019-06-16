def call(Map params) {
    def map = [:]
    map = ['distribution':'2632d5e4376ad7aa300fd70e3b8254504573a294']

    def lang = params.get('lang')
    def app = params.get('app')
    def artifact = params.get('artifact')

    log.debug("lang = ${lang}, app = ${app}, artifact = ${artifact}")

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