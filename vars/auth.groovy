def call() {
    timeout(time: 1, unit: 'MINUTES') {
        input (
            message: "即将发布到 ${params.BUILD_ENV} 环境，发布或者停止",
            ok: "确定",
            submitter: "gaowei@shixh.com,zhanghang0@shixh.com,lilongfei@shixh.com,chengmangmang@shixh.com,yangyin@shixh.com,qulu@shixh.com,huanghua@shixh.com,shiminghua@shixh.com,lumeng@shixh.com,zhangxiulong@shixh.com,chengjian@shixh.com,xiongjinping@shixh.com,gaopeng@shixh.com,simanman@shixh.com,changpeng@shixh.com,chenyantao@shixh.com,xiaolihong@shixh.com"
        )
    }
}