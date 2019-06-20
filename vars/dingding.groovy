def call (boolean success) {

    def text = success ? '构建成功' : '构建失败'
    def url = success ? 'http://icons.iconarchive.com/icons/paomedia/small-n-flat/1024/sign-check-icon.png' : 'http://www.iconsdb.com/icons/preview/soylent-red/x-mark-3-xxl.png'
    def token = "${DEV_DingDing_TOKEN}"

    if (params.BUILD_BRANCH == 'test') {
        token = "${UAT_DingDing_TOKEN}"
    }

    def patchOrg = """
        {
            "msgtype": "link", 
            "link": {
                "title": "app : $JOB_BASE_NAME",
                "text": "[${JOB_BASE_NAME}: #${BUILD_NUMBER}] ${text}",
                "picUrl": "${url}", 
                "messageUrl": "${BUILD_URL}"
            }
        }
    """

    def response = httpRequest requestBody: patchOrg, contentType: 'APPLICATION_JSON_UTF8', httpMode: 'POST', url: "https://oapi.dingtalk.com/robot/send?access_token=${token}"
    println('Status: '+response.status)
    println('Response: '+response.content)
}