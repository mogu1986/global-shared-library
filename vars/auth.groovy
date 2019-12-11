def call() {
    timeout(time: 1, unit: 'MINUTES') {
        input (
            message: "即将发布到 ${params.BUILD_ENV} 环境，发布或者停止",
            ok: "确定",
            submitter: "gaowei@shixh.com"
        )
    }
}
