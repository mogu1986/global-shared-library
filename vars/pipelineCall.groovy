def call(String app="${JOB_NAME}", String lang = "${JOB_NAME}") {

    log.debug("app = ${app}")

    def params = new com.sxh.AppMeta().getMeta(app)
    
    if (params != null) {
        lang = params.get('lang')
        log.debug("${app} 查找结果 : ${params}")
    }

    log.debug("lang : ${lang}")

    switch (lang) {
        case "jar":
            pipeline_jar(params)
            break
        case "java":
            pipeline_java(params)
            break
        case "tomcat":
            pipeline_tomcat(params)
            break
        case "nodejs":
            pipeline_nodejs(params)
            break
        case "html":
            pipeline_html(params)
            break
        case "aliyun-harbor":
            pipeline_harbor(params)
            break
        case "nodejsyqm":
            pipeline_nodejsyqm(params)
            break
        case "wx":
            pipeline_wx(params)
            break
        case "h5web":
            pipeline_h5web(params)
            break
        case "release":
            pipeline_release(params)
            break
        case "release_k8s":
            pipeline_release_k8s(params)
            break
        case "aliyun-sync-war":
            pipeline_sync_war(params)
            break
        case "aliyun-sync-war-wx":
            pipeline_sync_war_wx(params)
            break
        case "aliyun-sync-war-h5web":
            pipeline_sync_war_h5web(params)
            break
        case "aliyun-harbor-nodejs":
            pipeline_sync_nodejs(params)
            break
        case "aliyun-harbor-html":
            pipeline_sync_html(params)
            break
        case "aliyun2qcloud":
            pipeline_aliyun2qcloud(params)
            break
        case "qcloud-sync-war":
            pipeline_qcloud_sync_war(params)
            break
        case "qcloud-sync-war-wx":
            pipeline_qcloud_sync_war_wx(params)
            break
        case "qcloud-sync-war-h5web":
            pipeline_qcloud_sync_war_h5web(params)
            break
        case "qcloud-release":
            pipeline_qcloud_release(params)
            break
        case "qcloud_release_k8s":
            pipeline_qcloud_release_k8s(params)
            break            
        default:
            println "nice to meet you"
    }

}

def putSonar(Map params) {

    def keys = ['sonar.sources', 'sonar.java.binaries']

    for (int i = 0; i < keys.size(); i++) {
        def key = keys.get(i)
        if (!params.containsKey(key)) {
            params.put(key, '.')
        }
    }

    log.debug("sonar.sources = ${params['sonar.sources']}")
    log.debug("sonar.java.binaries = ${params['sonar.java.binaries']}")
}
