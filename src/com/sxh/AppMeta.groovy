package com.sxh

class AppMeta {

    def meta = [
            [
                    app: "demo-provider",
                    lang: 'java',
                    group: 'dubbo',
                    appPort: 30001,
                    artifact: 'dubbo-demo-provider/target/dubbo-demo-provider.jar',
                    git: 'http://gitlab.shixhlocal.com/gaowei/dubbo-demo.git',
                    javaOpts: [
                            'mit': '-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.registry.address=zookeeper://10.50.4.207:2181 -Ddubbo.protocol.port=20880 -Dserver.port=8080',
                            'sit': '-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.registry.address=zookeeper://10.50.4.207:2181 -Ddubbo.protocol.port=20880 -Dserver.port=8080',
                            'uat': '-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.registry.address=zookeeper://10.50.4.207:2181 -Ddubbo.protocol.port=20880 -Dserver.port=8080'
                    ],
                    portArgs: '-p 30001:20880 -p 30002:8080'
            ],
            [
                    app: "distribution",
                    lang: 'java',
                    namespace: 'ops',
                    group: 'web',
                    appPort: 20002,
                    artifact: 'distribution-service/target/distribution-service.jar',
                    git: 'http://gitlab.shixhlocal.com/gaowei/distribution.git',
                    javaOpts: [
                            'mit': '-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.registry.address=zookeeper://10.50.4.207:2181 -Ddubbo.protocol.port=20880 -Dserver.port=8080',
                            'sit': '-Duser.timezone=Asia/wh -Djava.security.egd=file:/dev/./urandom -Ddubbo.registry.address=zookeeper://10.50.4.207:2181 -Ddubbo.protocol.port=20880 -Dserver.port=8080',
                            'uat': '-Duser.timezone=Asia/hz -Djava.security.egd=file:/dev/./urandom -Ddubbo.registry.address=zookeeper://10.50.4.207:2181 -Ddubbo.protocol.port=20880 -Dserver.port=8080'
                    ],
                    portArgs: '-p 20002:8080'
            ],
            // oms 304
            [
                    app: "order-sdk",
                    lang: 'jar',
                    git: 'http://gitlab.shixhlocal.com/oms/order-sdk.git',
                    isSonar: true
            ],
            // 比较另类的一个
            [
                    app: "h5-web",
                    lang: 'h5web',
                    group: 'jfb_ec_web',
                    appPort: 30313,
                    artifact: 'target/jfb_ec_wx-1.1.0-RELEASES.war',
                    git: 'http://gitlab.shixhlocal.com/front/h5-web.git',
                    javaOpts: '-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                    portArgs: '-p 30313:8080'
            ],
            //  portal-ui
            [
                    app: "portal-ui",
                    namespace: "frame",
                    lang: 'nodejs',
                    group: 'vue',
                    appPort: 80,
                    artifact: 'dist',
                    git: 'http://gitlab.shixhlocal.com/frame/portal-ui.git',
                    run: 'docker',
                    portArgs: '-p 80:80',
                    binaryConfig: true
            ]
    ]

    def getMeta(String app) {
        def result = meta.find { value ->

            if (value.app.equals(app)) {
                return value
            }
            return null
        }
        return result
    }

    def getNamespace(String app) {
        def meta = this.getMeta(app)
        if (meta == null) {
            return null
        }
        return meta.get('namespace')
    }

    def getJavaOpts(Map map, String env) {
        def optsMap = map.get('javaOpts')
        def opts = optsMap.get(env)
        map.put('javaOpts', opts)
    }

    def getStr(String json) {
        def jsonSlpuer = new groovy.json.JsonSlurperClassic()
        def rs = jsonSlpuer.parseText(json)
        return rs
    }

}
