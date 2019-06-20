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
    def response = httpRequest requestBody: patchOrg, contentType: 'APPLICATION_JSON_UTF8', httpMode: 'POST', url: "https://oapi.dingtalk.com/robot/send?access_token=006db0f1009885a2f6af320c3391f5b911a37f24e395e78cd79a134ab714de48"
    println('Status: '+response.status)
    println('Response: '+response.content)
}