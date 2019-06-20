def call (boolean success) {

    def text = success ? 'SUCCESS' : 'FAILURE'
    def url = success ? 'http://icons.iconarchive.com/icons/paomedia/small-n-flat/1024/sign-check-icon.png' : 'http://www.iconsdb.com/icons/preview/soylent-red/x-mark-3-xxl.png'
    def token = "${DEV_DingDing_TOKEN}"

    if (params.BUILD_BRANCH == 'test') {
        token = "${UAT_DingDing_TOKEN}"
    }

    def patchOrg = """
        {
            "msgtype": "link", 
            "link": {
                "title": "app : ${APP}",
                "text": "构建名称：$JOB_BASE_NAME
构建编号：${BUILD_NUMBER}
构建结果：${text}", 
                "picUrl": "${url}", 
                "messageUrl": "${BUILD_URL}"
            }
        }
    """

    def response = httpRequest requestBody: patchOrg, contentType: 'APPLICATION_JSON_UTF8', httpMode: 'POST', url: "https://oapi.dingtalk.com/robot/send?access_token=${token}"
    println('Status: '+response.status)
    println('Response: '+response.content)
}