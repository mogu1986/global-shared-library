def call (boolean success) {

    def text = success ? 'SUCCESS' : 'FAILURE'
    def url = success ? 'http://icons.iconarchive.com/icons/paomedia/small-n-flat/1024/sign-check-icon.png' : 'http://www.iconsdb.com/icons/preview/soylent-red/x-mark-3-xxl.png'
    def token = "${DEV_DingDing_TOKEN}"

    if (isTest()) {
        token = "${UAT_DingDing_TOKEN}"
    }

    def patchOrg = """
        {
            "msgtype": "link", 
            "link": {
                "title": "${APP} 构建${text}",
                "text": "构建名称：$JOB_BASE_NAME
构建编号：${BUILD_NUMBER}
构建结果：${zuser}", 
                "picUrl": "${url}", 
                "messageUrl": "${BUILD_URL}"
            }
        }
    """

    def response = httpRequest(
            url: "https://oapi.dingtalk.com/robot/send?access_token=${token}",
            httpMode: 'POST',
            requestBody: patchOrg,
            contentType: 'APPLICATION_JSON_UTF8'
    )
    println('Status: '+response.status)
    println('Response: '+response.content)
}