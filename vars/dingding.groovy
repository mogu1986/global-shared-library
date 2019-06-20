def call (String text, String url) {
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

    def token = "${DEV_DingDing_TOKEN}"
    if (params.BUILD_BRANCH == 'test') {
        token = "${UAT_DingDing_TOKEN}"
    }

    def response = httpRequest requestBody: patchOrg, contentType: 'APPLICATION_JSON_UTF8', httpMode: 'POST', url: "https://oapi.dingtalk.com/robot/send?access_token=${token}"
    println('Status: '+response.status)
    println('Response: '+response.content)
}