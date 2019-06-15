def call(Map params) {
    def map = [:]
    map = ['distribution':'2632d5e4376ad7aa300fd70e3b8254504573a294']

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
        default:
            println "nice to meet you"
    }
}