package com.sxh;

class AppMeta {

    def meta = [
            // 301 frame
            [
                    app: "frame",
                    lang: 'jar',
                    git: 'http://gitlab.shixhlocal.com/frame/sxh-framework-project.git',
                    isSonar: false
            ],
            [
                    app: "rbac-sdk",
                    lang: 'jar',
                    git: 'http://gitlab.shixhlocal.com/frame/rbac-sdk.git',
                    isSonar: true
            ],
            [
                    app: "rbac-provider",
                    namespace: 'frame',
                    lang: 'java',
                    group: 'dubbo',
                    appPort: 30101,
                    artifact: 'target/rbac-provider.jar',
                    git: 'http://gitlab.shixhlocal.com/frame/rbac-provider.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880',
                            'uat': '-server -Xms4096m -Xmx4096m -XX:NewRatio=4 -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelInitialMarkEnabled -XX:+CMSParallelRemarkEnabled -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80 -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880'
                    ],
                    portArgs: '-p 30101:20880'
            ],
            [
                    app: "rbac-web",
                    namespace: 'frame',
                    lang: 'java',
                    group: 'web',
                    appPort: 30102,
                    artifact: 'target/rbac-web.jar',
                    git: 'http://gitlab.shixhlocal.com/frame/rbac-web.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'uat': '-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080'
                    ],
                    portArgs: '-p 30102:8080'
            ],
            [
                    app: "sso-sdk",
                    lang: 'jar',
                    git: 'http://gitlab.shixhlocal.com/frame/sso-sdk.git',
                    isSonar: true
            ],
            [
                    app: "sso-web",
                    namespace: 'frame',
                    lang: 'java',
                    group: 'web',
                    appPort: 30103,
                    artifact: 'target/sso-web.jar',
                    git: 'http://gitlab.shixhlocal.com/frame/sso-web.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'uat': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080'
                    ],
                    portArgs: '-p 30103:8080'
            ],
            [
                    app: "notice-sdk",
                    lang: 'jar',
                    git: 'http://gitlab.shixhlocal.com/frame/notice-sdk.git',
                    isSonar: true
            ],
            [
                    app: "notice-provider",
                    namespace: 'frame',
                    lang: 'java',
                    group: 'dubbo',
                    appPort: 30104,
                    artifact: 'target/notice-provider.jar',
                    git: 'http://gitlab.shixhlocal.com/frame/notice-provider.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dserver.port=8080 -Dxxl.job.executor.port=9991',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dserver.port=8080 -Dxxl.job.executor.port=9991',
                            'uat': '-server -Xms4096m -Xmx4096m -XX:NewRatio=4 -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelInitialMarkEnabled -XX:+CMSParallelRemarkEnabled -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80 -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dserver.port=8080 -Dxxl.job.executor.port=9991'
                    ],
                    portArgs: '-p 30104:20880 -p 30105:9991'
            ],
              [
                    app: "code-sdk",
                    lang: 'jar',
                    git: 'http://gitlab.shixhlocal.com/frame/code-sdk.git',
                    isSonar: true
            ],
            [
                    app: "code-provider",
                    namespace: 'frame',
                    lang: 'java',
                    group: 'dubbo',
                    appPort: 30106,
                    artifact: 'target/code-provider.jar',
                    git: 'http://gitlab.shixhlocal.com/frame/code-provider.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880',
                            'uat': '-server -Xms4096m -Xmx4096m -XX:NewRatio=4 -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelInitialMarkEnabled -XX:+CMSParallelRemarkEnabled -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80 -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880'
                    ],
                    portArgs: '-p 30106:20880'
            ],
            [
                    app: "es-job",
                    namespace: 'frame',
                    lang: 'java',
                    group: 'dubbo',
                    appPort: 30107,
                    artifact: 'target/es-job.jar',
                    git: 'http://gitlab.shixhlocal.com/frame/es-job.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dserver.port=8080 -Dxxl.job.executor.port=9991',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dserver.port=8080 -Dxxl.job.executor.port=9991',
                            'uat': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dserver.port=8080 -Dxxl.job.executor.port=9991'
                    ],
                    portArgs: '-p 30107:20880 -p 30108:8080 -p 30109:9991'
            ],
            [
                    app: "openapi-web",
                    namespace: 'frame',
                    lang: 'java',
                    group: 'web',
                    appPort: 30110,
                    artifact: 'target/openapi-web.jar',
                    git: 'http://gitlab.shixhlocal.com/frame/openapi-web.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'uat': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080'
                    ],
                    portArgs: '-p 30110:8080'
            ],
            [
                    app: "bank-sdk",
                    lang: 'jar',
                    git: 'http://gitlab.shixhlocal.com/frame/bank-sdk.git'
            ],
            [
                    app: "bank-provider",
                    namespace: 'frame',
                    lang: 'java',
                    group: 'dubbo',
                    appPort: 30111,
                    artifact: 'target/bank-provider.jar',
                    git: 'http://gitlab.shixhlocal.com/frame/bank-provider.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991',
                            'uat': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991'
                    ],
                    portArgs: '-p 30111:20880 -p 30112:9991'
            ],
            // crm 302
            [
                    app: "usercenter-sdk",
                    lang: 'jar',
                    git: 'http://gitlab.shixhlocal.com/crm/usercenter-sdk.git',
                    isSonar: true
            ],
            [
                    app: "usercenter-provider",
                    namespace: 'crm',
                    lang: 'java',
                    group: 'dubbo',
                    appPort: 30201,
                    artifact: 'target/usercenter-provider.jar',
                    git: 'http://gitlab.shixhlocal.com/crm/usercenter-provider.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880',
                            'uat': '-server -Xms4g -Xmx4g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880'
                    ],
                    portArgs: '-p 30201:20880'
            ],
            [
                    app: "usercenter-web",
                    namespace: 'crm',
                    lang: 'java',
                    group: 'web',
                    appPort: 30202,
                    artifact: 'target/usercenter-web.jar',
                    git: 'http://gitlab.shixhlocal.com/crm/usercenter-web.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'uat': '-server -Xms4g -Xmx4g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080'
                    ],
                    portArgs: '-p 30202:8080'
            ],
            [
                    app: "h5api-sdk",
                    lang: 'jar',
                    git: 'http://gitlab.shixhlocal.com/crm/h5api-sdk.git',
                    isSonar: true
            ],
            [
                    app: "h5api-web",
                    namespace: 'crm',
                    lang: 'java',
                    group: 'web',
                    appPort: 30203,
                    artifact: 'target/h5api-web.jar',
                    git: 'http://gitlab.shixhlocal.com/crm/h5api-web.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'uat': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080'
                    ],
                    portArgs: '-p 30203:8080'
            ],
            // front 303
            [
                    app: "es-sdk",
                    lang: 'jar',
                    git: 'http://gitlab.shixhlocal.com/front/es-sdk.git',
                    isSonar: true
            ],
            [
                    app: "es-provider",
                    namespace: 'front',
                    lang: 'java',
                    group: 'dubbo',
                    appPort: 30301,
                    artifact: 'target/es-provider.jar',
                    git: 'http://gitlab.shixhlocal.com/front/es-provider.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dserver.port=8080 -Dxxl.job.executor.port=30306',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dserver.port=8080 -Dxxl.job.executor.port=30306',
                            'uat': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dserver.port=8080 -Dxxl.job.executor.port=30306'
                    ],
                    portArgs: '-p 30301:20880 -p 30302:8080 -p 30306:30306'
            ],
            [
                    app: "app-wechat-web",
                    namespace: 'front',
                    lang: 'java',
                    group: 'web',
                    appPort: 30303,
                    artifact: 'target/app-wechat-web.jar',
                    git: 'http://gitlab.shixhlocal.com/front/app-wechat-web.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'uat': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080'
                    ],
                    portArgs: '-p 30303:8080'
            ],
            [
                    app: "market-sdk",
                    lang: 'jar',
                    git: 'http://gitlab.shixhlocal.com/front/market-sdk.git',
                    isSonar: true
            ],
            [
                    app: "market-provider",
                    namespace: 'front',
                    lang: 'java',
                    group: 'dubbo',
                    appPort: 30304,
                    artifact: 'target/market-provider.jar',
                    git: 'http://gitlab.shixhlocal.com/front/market-provider.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991',
                            'uat': '-server -Xms4096m -Xmx4096m -XX:NewRatio=4 -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelInitialMarkEnabled -XX:+CMSParallelRemarkEnabled -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80 -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991'
                    ],
                    portArgs: '-p 30304:20880 -p 30307:9991'
            ],
            [
                    app: "market-web",
                    namespace: 'front',
                    lang: 'java',
                    group: 'web',
                    appPort: 30305,
                    artifact: 'target/market-web.jar',
                    git: 'http://gitlab.shixhlocal.com/front/market-web.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'uat': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080'
                    ],
                    portArgs: '-p 30305:8080'
            ],
            [
                    app: "supply-sdk",
                    lang: 'jar',
                    git: 'http://gitlab.shixhlocal.com/front/supply-sdk.git',
                    isSonar: true
            ],
            [
                    app: "supply-provider",
                    namespace: 'front',
                    lang: 'java',
                    group: 'dubbo',
                    appPort: 30308,
                    artifact: 'target/supply-provider.jar',
                    git: 'http://gitlab.shixhlocal.com/front/supply-provider.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991',
                            'uat': '-server -Xms4096m -Xmx4096m -XX:NewRatio=4 -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelInitialMarkEnabled -XX:+CMSParallelRemarkEnabled -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80 -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991'
                    ],
                    portArgs: '-p 30308:20880 -p 30311:9991'
            ],
            [
                    app: "supply-web",
                    namespace: 'front',
                    lang: 'java',
                    group: 'web',
                    appPort: 30309,
                    artifact: 'target/supply-web.jar',
                    git: 'http://gitlab.shixhlocal.com/front/supply-web.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991',
                            'uat': '-server -Xms4096m -Xmx4096m -XX:NewRatio=4 -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelInitialMarkEnabled -XX:+CMSParallelRemarkEnabled -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80 -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080'
                    ],
                    portArgs: '-p 30309:8080'
            ],
            [
                    app: "supply-ui",
                    namespace: 'front',
                    lang: 'nodejs',
                    group: 'vue',
                    appPort: 30310,
                    artifact: 'dist',
                    git: 'http://gitlab.shixhlocal.com/front/supply-ui.git',
                    run: 'docker',
                    portArgs: '-p 30310:80',
                    binaryConfig: true
            ],
            //  website
            [
                    app: "website",
                    namespace: "frame",
                    lang: 'html',
                    group: 'vue',
                    appPort: 30312,
                    artifact: 'dist',
                    git: 'http://gitlab.shixhlocal.com/front/website.git',
                    run: 'docker',
                    portArgs: '-p 30312:80',
                    binaryConfig: true
            ],
            [
                    app: "cms-sdk",
                    lang: 'jar',
                    git: 'http://gitlab.shixhlocal.com/cms/front-sdk.git',
                    isSonar: true
            ],
            [
                    app: "cms-provider",
                    namespace: 'front',
                    lang: 'java',
                    group: 'dubbo',
                    appPort: 30314,
                    artifact: 'target/cms-provider.jar',
                    git: 'http://gitlab.shixhlocal.com/front/cms-provider.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991',
                            'uat': '-server -Xms4096m -Xmx4096m -XX:NewRatio=4 -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelInitialMarkEnabled -XX:+CMSParallelRemarkEnabled -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80 -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991'
                    ],
                    portArgs: '-p 30314:20880 -p 30315:9991'
            ],
            [
                    app: "cms-web",
                    namespace: 'front',
                    lang: 'java',
                    group: 'web',
                    appPort: 30316,
                    artifact: 'target/cms-web.jar',
                    git: 'http://gitlab.shixhlocal.com/front/cms-web.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'uat': '-server -Xms4096m -Xmx4096m -XX:NewRatio=4 -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelInitialMarkEnabled -XX:+CMSParallelRemarkEnabled -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80 -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080'
                    ],
                    portArgs: '-p 30316:8080'
            ],
            [
                    app: "cms-ui",
                    namespace: 'front',
                    lang: 'nodejs',
                    group: 'vue',
                    appPort: 30317,
                    artifact: 'dist',
                    git: 'http://gitlab.shixhlocal.com/front/cms-ui.git',
                    run: 'docker',
                    portArgs: '-p 30317:80',
                    binaryConfig: true
            ],
            // oms 304
            [
                    app: "order-sdk",
                    lang: 'jar',
                    git: 'http://gitlab.shixhlocal.com/oms/order-sdk.git',
                    isSonar: true
            ],
            [
                    app: "order-provider",
                    namespace: 'oms',
                    lang: 'java',
                    group: 'dubbo',
                    appPort: 30401,
                    artifact: 'target/order-provider.jar',
                    git: 'http://gitlab.shixhlocal.com/oms/order-provider.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991',
                            'uat': '-server -Xms4096m -Xmx4096m -XX:NewRatio=4 -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelInitialMarkEnabled -XX:+CMSParallelRemarkEnabled -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80 -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991'
                    ],
                    portArgs: '-p 30401:20880 -p 30404:9991',
                    binaryConfig: true
            ],
            [
                    app: "order-web",
                    namespace: 'oms',
                    lang: 'java',
                    group: 'web',
                    appPort: 30402,
                    artifact: 'target/order-web.jar',
                    git: 'http://gitlab.shixhlocal.com/oms/order-web.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'uat': '-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080'
                    ],
                    portArgs: '-p 30402:8080'
            ],
            [
                    app: "pay-notify-web",
                    namespace: 'oms',
                    lang: 'java',
                    group: 'web',
                    appPort: 30403,
                    artifact: 'target/pay-notify-web.jar',
                    git: 'http://gitlab.shixhlocal.com/oms/pay-notify-web.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'uat': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080'
                    ],
                    portArgs: '-p 30403:8080'
            ],
            // wms 305
            [
                    app: "fahuo-sdk",
                    lang: 'jar',
                    git: 'http://gitlab.shixhlocal.com/bizplatform-wms/fahuo-sdk.git',
                    isSonar: true
            ],
            [
                    app: "fahuo-provider",
                    namespace: 'wms',
                    lang: 'java',
                    group: 'dubbo',
                    appPort: 30501,
                    artifact: 'target/fahuo-provider.jar',
                    git: 'http://gitlab.shixhlocal.com/bizplatform-wms/fahuo-provider.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991',
                            'uat': '-server -Xms4096m -Xmx4096m -XX:NewRatio=4 -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelInitialMarkEnabled -XX:+CMSParallelRemarkEnabled -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80 -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991'
                    ],
                    portArgs: '-p 30501:20880 -p 30505:9991'
            ],
            [
                    app: "fahuo-web",
                    namespace: 'wms',
                    lang: 'java',
                    group: 'web',
                    appPort: 30502,
                    artifact: 'target/wms-fahuo-web.jar',
                    git: 'http://gitlab.shixhlocal.com/bizplatform-wms/fahuo-web.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'uat': '-server -Xms4g -Xmx4g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080'
                    ],
                    portArgs: '-p 30502:8080'
            ],
            [
                    app: "stock-sdk",
                    lang: 'jar',
                    git: 'http://gitlab.shixhlocal.com/bizplatform-wms/stock-sdk.git',
                    isSonar: true
            ],
            [
                    app: "stock-provider",
                    namespace: 'wms',
                    lang: 'java',
                    group: 'dubbo',
                    appPort: 30503,
                    artifact: 'target/stock-provider.jar',
                    git: 'http://gitlab.shixhlocal.com/bizplatform-wms/stock-provider.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991',
                            'uat': '-server -Xms4096m -Xmx4096m -XX:NewRatio=4 -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelInitialMarkEnabled -XX:+CMSParallelRemarkEnabled -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80 -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991'
                    ],
                    portArgs: '-p 30503:20880 -p 30507:9991'
            ],
            [
                    app: "stock-web",
                    namespace: 'wms',
                    lang: 'java',
                    group: 'web',
                    appPort: 30504,
                    artifact: 'target/stock-web.jar',
                    git: 'http://gitlab.shixhlocal.com/bizplatform-wms/stock-web.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'uat': '-server -Xms4g -Xmx4g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080'
                    ],
                    portArgs: '-p 30504:8080'
            ],
            [
                    app: "stock-ui",
                    namespace: 'wms',
                    lang: 'nodejsyqm',
                    group: 'vue',
                    appPort: 30506,
                    artifact: 'dist',
                    git: 'http://gitlab.shixhlocal.com/bizplatform-wms/stock-ui.git',
                    run: 'docker',
                    portArgs: '-p 30506:80',
                    binaryConfig: true
            ],
            // purchase 306
            [
                    app: "product-sdk",
                    lang: 'jar',
                    git: 'http://gitlab.shixhlocal.com/purchase/product-sdk.git',
                    isSonar: true
            ],
            [
                    app: "product-provider",
                    namespace: 'purchase',
                    lang: 'java',
                    group: 'dubbo',
                    appPort: 30601,
                    artifact: 'target/product-provider.jar',
                    git: 'http://gitlab.shixhlocal.com/purchase/product-provider.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991',
                            'uat': '-server -Xms4096m -Xmx4096m -XX:NewRatio=4 -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelInitialMarkEnabled -XX:+CMSParallelRemarkEnabled -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80 -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991'
                    ],
                    portArgs: '-p 30601:20880 -p 30604:9991'
            ],
            [
                    app: "purchase-sdk",
                    lang: 'jar',
                    git: 'http://gitlab.shixhlocal.com/purchase/purchase-sdk.git',
                    isSonar: true
            ],
            [
                    app: "purchase-provider",
                    namespace: 'purchase',
                    lang: 'java',
                    group: 'dubbo',
                    appPort: 30602,
                    artifact: 'target/purchase-provider.jar',
                    git: 'http://gitlab.shixhlocal.com/purchase/purchase-provider.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880',
                            'uat': '-server -Xms4096m -Xmx4096m -XX:NewRatio=4 -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelInitialMarkEnabled -XX:+CMSParallelRemarkEnabled -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80 -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991'
                    ],
                    portArgs: '-p 30602:20880 -p 30605:9991'
            ],
            [
                    app: "purchase-web",
                    namespace: 'purchase',
                    lang: 'java',
                    group: 'web',
                    appPort: 30603,
                    artifact: 'target/purchase-web.jar',
                    git: 'http://gitlab.shixhlocal.com/purchase/purchase-web.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'uat': '-server -Xms4g -Xmx4g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080'
                    ],
                    portArgs: '-p 30603:8080'
            ],
            [
                    app: "finance-sdk",
                    lang: 'jar',
                    git: 'http://gitlab.shixhlocal.com/finance/finance-sdk.git',
                    isSonar: false
            ],
            [
                    app: "finance-provider",
                    namespace: 'finance',
                    lang: 'java',
                    group: 'dubbo',
                    appPort: 30701,
                    artifact: 'target/finance-provider.jar',
                    git: 'http://gitlab.shixhlocal.com/finance/finance-provider.git',
                    javaOpts: [
                            'mit': '-server -Xms4g -Xmx4g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991',
                            'uat': '-server -Xms4096m -Xmx4096m -XX:NewRatio=4 -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelInitialMarkEnabled -XX:+CMSParallelRemarkEnabled -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80 -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991'
                    ],
                    portArgs: '-p 30701:20880 -p 30702:9991'
            ],
             [
                    app: "finance-web",
                    namespace: 'finance',
                    lang: 'java',
                    group: 'web',
                    appPort: 30703,
                    artifact: 'target/finance-web.jar',
                    git: 'http://gitlab.shixhlocal.com/finance/finance-web.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'uat': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080'
                    ],
                    portArgs: '-p 30703:8080'
            ],
            //bi
            [
                    app: "bizdata-sdk",
                    lang: 'jar',
                    git: 'http://gitlab.shixhlocal.com/bi/bizdata-sdk.git',
                    isSonar: true
            ],
            [
                    app: "bizdata-provider",
                    namespace: 'bi',
                    lang: 'java',
                    group: 'dubbo',
                    appPort: 30801,
                    artifact: 'target/bizdata-provider.jar',
                    git: 'http://gitlab.shixhlocal.com/bi/bizdata-provider.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880',
                            'uat': '-server -Xms4096m -Xmx4096m -XX:NewRatio=4 -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelInitialMarkEnabled -XX:+CMSParallelRemarkEnabled -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80 -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880'
                    ],
                    portArgs: '-p 30801:20880'
            ],
            [
                    app: "bizdata-web",
                    namespace: 'bi',
                    lang: 'java',
                    group: 'web',
                    appPort: 30802,
                    artifact: 'target/bizdata-web.jar',
                    git: 'http://gitlab.shixhlocal.com/bi/bizdata-web.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'uat': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080'
                    ],
                    portArgs: '-p 30802:8080'
            ],
            [
                    app: "datacenter-sdk",
                    lang: 'jar',
                    git: 'http://gitlab.shixhlocal.com/bi/datacenter-sdk.git',
                    isSonar: true
            ],
            [
                    app: "datacenter-provider",
                    namespace: 'bi',
                    lang: 'java',
                    group: 'dubbo',
                    appPort: 30803,
                    artifact: 'target/datacenter-provider.jar',
                    git: 'http://gitlab.shixhlocal.com/bi/datacenter-provider.git',
                    javaOpts: [
                            'mit': '-server -Xms512m -Xmx512m -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991 -DLOG_PATH=/logs',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991 -DLOG_PATH=/logs',
                            'uat': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880 -Dxxl.job.executor.port=9991 -DLOG_PATH=/logs'
                    ],
                    portArgs: '-p 30803:20880 -p 30804:9991'
            ],
            [
                    app: "datacenter-web",
                    namespace: 'bi',
                    lang: 'java',
                    group: 'dubbo',
                    appPort: 30805,
                    artifact: 'target/datacenter-web.jar',
                    git: 'http://gitlab.shixhlocal.com/bi/datacenter-web.git',
                    javaOpts: [
                            'mit': '-server -Xms512m -Xmx512m -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'uat': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080'
                    ],
                    portArgs: '-p 30805:8080'
            ],
            //fws
            [
                    app: "fws-sdk",
                    lang: 'jar',
                    git: 'http://gitlab.shixhlocal.com/fws/fws-sdk.git',
                    isSonar: true
            ],
            [
                    app: "fws-provider",
                    namespace: 'fws',
                    lang: 'java',
                    group: 'dubbo',
                    appPort: 30901,
                    artifact: 'target/fws-provider.jar',
                    git: 'http://gitlab.shixhlocal.com/fws/fws-provider.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880',
                            'uat': '-server -Xms4096m -Xmx4096m -XX:NewRatio=4 -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelInitialMarkEnabled -XX:+CMSParallelRemarkEnabled -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80 -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880'
                    ],
                    portArgs: '-p 30901:20880'
            ],
            [
                    app: "fws-web",
                    namespace: 'fws',
                    lang: 'java',
                    group: 'web',
                    appPort: 30902,
                    artifact: 'target/fws-web.jar',
                    git: 'http://gitlab.shixhlocal.com/fws/fws-web.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'uat': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080'
                    ],
                    portArgs: '-p 30902:8080'
            ],
            [
                    app: "fws-bi-sdk",
                    lang: 'jar',
                    git: 'http://gitlab.shixhlocal.com/fws/fws-bi-sdk.git',
                    isSonar: true
            ],
            [
                    app: "fws-bi-provider",
                    namespace: 'fws',
                    lang: 'java',
                    group: 'dubbo',
                    appPort: 30903,
                    artifact: 'target/fws-bi-provider.jar',
                    git: 'http://gitlab.shixhlocal.com/fws/fws-bi-provider.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880',
                            'uat': '-server -Xms4096m -Xmx4096m -XX:NewRatio=4 -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelInitialMarkEnabled -XX:+CMSParallelRemarkEnabled -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80 -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880'
                    ],
                    portArgs: '-p 30903:20880'
            ],
            [
                    app: "fws-upms-sdk",
                    lang: 'jar',
                    git: 'http://gitlab.shixhlocal.com/fws/fws-upms-sdk.git',
                    isSonar: true
            ],
            [
                    app: "fws-upms-provider",
                    namespace: 'fws',
                    lang: 'java',
                    group: 'dubbo',
                    appPort: 30904,
                    artifact: 'target/fws-upms-provider.jar',
                    git: 'http://gitlab.shixhlocal.com/fws/fws-upms-provider.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880',
                            'uat': '-server -Xms4096m -Xmx4096m -XX:NewRatio=4 -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelInitialMarkEnabled -XX:+CMSParallelRemarkEnabled -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80 -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880'
                    ],
                    portArgs: '-p 30904:20880'
            ],
            [
                    app: "fws-log-sdk",
                    lang: 'jar',
                    git: 'http://gitlab.shixhlocal.com/fws/fws-log-sdk.git',
                    isSonar: true
            ],
            [
                    app: "fws-log-provider",
                    namespace: 'fws',
                    lang: 'java',
                    group: 'dubbo',
                    appPort: 30905,
                    artifact: 'target/fws-log-provider.jar',
                    git: 'http://gitlab.shixhlocal.com/fws/fws-log-provider.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880',
                            'uat': '-server -Xms4096m -Xmx4096m -XX:NewRatio=4 -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelInitialMarkEnabled -XX:+CMSParallelRemarkEnabled -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80 -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880'
                    ],
                    portArgs: '-p 30905:20880'
            ],
            [
                    app: "fws-job",
                    namespace: 'fws',
                    lang: 'java',
                    group: 'dubbo',
                    appPort: 30906,
                    artifact: 'target/fws-job.jar',
                    git: 'http://gitlab.shixhlocal.com/fws/fws-job.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Ddubbo.protocol.port=20880',
                            'uat': '-server -Xms4096m -Xmx4096m -XX:NewRatio=4 -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelInitialMarkEnabled -XX:+CMSParallelRemarkEnabled -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80 -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dxxl.job.executor.port=9991'
                    ],
                    portArgs: '-p 30906:9991'
            ],
            [
                    app: "fws-center-web",
                    namespace: 'fws',
                    lang: 'java',
                    group: 'web',
                    appPort: 30907,
                    artifact: 'target/fws-center-web.jar',
                    git: 'http://gitlab.shixhlocal.com/fws/fws-center-web.git',
                    javaOpts: [
                            'mit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'sit': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                            'uat': '-server -Xms1g -Xmx1g -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080'
                    ],
                    portArgs: '-p 30907:8080'
            ],
            [
                    app: "fws-center-ui",
                    namespace: 'fws',
                    lang: 'nodejsyqm',
                    group: 'vue',
                    appPort: 30908,
                    artifact: 'dist',
                    git: 'http://gitlab.shixhlocal.com/fws/fws-center-ui.git',
                    run: 'docker',
                    portArgs: '-p 30908:80',
                    binaryConfig: true
            ],
            // tomcat old project  sxh
            [
                    app: "admin",
                    lang: 'tomcat',
                    group: 'jfb_ec_web',
                    appPort: 33101,
                    artifact: 'target/jfb_ec_admin-1.1.0-RELEASES.war',
                    git: 'http://gitlab.shixhlocal.com/bizplatform/wechat-admin-h5.git',
                    javaOpts: '-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                    portArgs: '-p 33101:8080'
            ],
            [
                    app: "api",
                    lang: 'tomcat',
                    group: 'jfb_ec_web',
                    appPort: 33102,
                    artifact: 'target/jfb_ec_api-1.1.0-RELEASES.war',
                    git: 'http://gitlab.shixhlocal.com/bizplatform/wechat-admin-h5.git',
                    javaOpts: '-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                    portArgs: '-p 33102:8080'
            ],
            [
                    app: "baseapi",
                    lang: 'tomcat',
                    group: 'jfb_ec_web',
                    appPort: 33103,
                    artifact: 'target/jfb_ec_baseapi-1.1.0-RELEASES.war',
                    git: 'http://gitlab.shixhlocal.com/bizplatform/wechat-admin-h5.git',
                    javaOpts: '-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                    portArgs: '-p 33103:8080'
            ],
            [
                    app: "pay",
                    lang: 'tomcat',
                    group: 'jfb_ec_web',
                    appPort: 33105,
                    artifact: 'target/jfb_ec_pay-1.1.0-RELEASES.war',
                    git: 'http://gitlab.shixhlocal.com/bizplatform/wechat-admin-h5.git',
                    javaOpts: '-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                    portArgs: '-p 33105:8080'
            ],
            [
                    app: "quartz",
                    lang: 'tomcat',
                    group: 'jfb_ec_web',
                    appPort: 33106,
                    artifact: 'target/jfb_ec_quartz-1.1.0-RELEASES.war',
                    git: 'http://gitlab.shixhlocal.com/bizplatform/wechat-admin-h5.git',
                    javaOpts: '-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                    portArgs: '-p 33106:8080'
            ],
            [
                    app: "callback",
                    lang: 'tomcat',
                    group: 'jfb_ec_web',
                    appPort: 33107,
                    artifact: 'target/jfb_ec_callback-1.1.0-RELEASES.war',
                    git: 'http://gitlab.shixhlocal.com/bizplatform/wechat-admin-h5.git',
                    javaOpts: '-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                    portArgs: '-p 33107:8080'
            ],
            [
                    app: "purchase",
                    lang: 'tomcat',
                    group: 'jfb_ec_web',
                    appPort: 33108,
                    artifact: 'target/jfb_ec_purchase-1.1.0-RELEASES.war',
                    git: 'http://gitlab.shixhlocal.com/bizplatform/wechat-admin-h5.git',
                    javaOpts: '-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                    portArgs: '-p 33108:8080'
            ],
            [
                    app: "gongmall",
                    lang: 'tomcat',
                    group: 'jfb_ec_web',
                    appPort: 33109,
                    artifact: 'target/jfb_ec_pay-1.1.0-RELEASES.war',
                    git: 'http://gitlab.shixhlocal.com/bizplatform/wechat-admin-h5.git',
                    javaOpts: '-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                    portArgs: '-p 33109:8080'
            ],
            [
                    app: "img",
                    lang: 'tomcat',
                    group: 'jfb_ec_web',
                    appPort: 33110,
                    artifact: 'target/jfb_ec_img-1.1.0-RELEASES.war',
                    git: 'http://gitlab.shixhlocal.com/bizplatform/wechat-admin-h5.git',
                    javaOpts: '-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                    portArgs: '-p 33110:8080'
            ],
            [
                    app: "supplyer",
                    lang: 'tomcat',
                    group: 'jfb_ec_web',
                    appPort: 33111,
                    artifact: 'target/jfb_ec_supplyer-1.1.0-RELEASES.war',
                    git: 'http://gitlab.shixhlocal.com/bizplatform/wechat-admin-h5.git',
                    javaOpts: '-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                    portArgs: '-p 33111:8080'
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
            //yqm tomcat
            [
                    app: "yqm_admin",
                    lang: 'tomcat',
                    group: 'yqm_web',
                    appPort: 33112,
                    artifact: 'target/yqm_ec_admin-1.0.0-RELEASES.war',
                    git: 'http://gitlab.shixhlocal.com/bizplatform/yqm.git',
                    javaOpts: '-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                    portArgs: '-p 33112:8080'
            ],
            [
                    app: "yqm_api",
                    lang: 'tomcat',
                    group: 'yqm_web',
                    appPort: 33113,
                    artifact: 'target/yqm_ec_api-1.0.0-RELEASES.war',
                    git: 'http://gitlab.shixhlocal.com/bizplatform/yqm.git',
                    javaOpts: '-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                    portArgs: '-p 33113:8080'
            ],
            [
                    app: "yqm_mq",
                    lang: 'tomcat',
                    group: 'yqm_web',
                    appPort: 33114,
                    artifact: 'target/yqm_ec_mq-1.0.0-RELEASES.war',
                    git: 'http://gitlab.shixhlocal.com/bizplatform/yqm.git',
                    javaOpts: '-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                    portArgs: '-p 33114:8080'
            ],
            [
                    app: "yqm_baseapi",
                    lang: 'tomcat',
                    group: 'yqm_web',
                    appPort: 33115,
                    artifact: 'target/yqm_ec_baseapi-1.0.0-RELEASES.war',
                    git: 'http://gitlab.shixhlocal.com/bizplatform/yqm.git',
                    javaOpts: '-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                    portArgs: '-p 33115:8080'
            ],
            [
                    app: "yqm_xxl",
                    lang: 'tomcat',
                    group: 'yqm_web',
                    appPort: 33116,
                    artifact: 'target/yqm_ec_xxl.war',
                    git: 'http://gitlab.shixhlocal.com/bizplatform/yqm.git',
                    javaOpts: '-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                    portArgs: '-p 33116:8080'
            ],
            //dinghuo tomcat
            [
                    app: "dinghuo_admin",
                    lang: 'tomcat',
                    group: 'yqm_web',
                    appPort: 33118,
                    artifact: 'target/dinghuo_ec_admin-1.0.0-RELEASES.war',
                    git: 'http://gitlab.shixhlocal.com/bizplatform-wms/dinghuo.git',
                    javaOpts: '-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                    portArgs: '-p 33118:8080'
            ],
            [
                    app: "dinghuo_api",
                    lang: 'tomcat',
                    group: 'yqm_web',
                    appPort: 33119,
                    artifact: 'target/dinghuo_ec_api-1.0.0-RELEASES.war',
                    git: 'http://gitlab.shixhlocal.com/bizplatform-wms/dinghuo.git',
                    javaOpts: '-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                    portArgs: '-p 33119:8080'
            ],
            [
                    app: "dinghuo_mq",
                    lang: 'tomcat',
                    group: 'yqm_web',
                    appPort: 33120,
                    artifact: 'target/dinghuo_ec_mq-1.0.0-RELEASES.war',
                    git: 'http://gitlab.shixhlocal.com/bizplatform-wms/dinghuo.git',
                    javaOpts: '-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                    portArgs: '-p 33120:8080'
            ],
            [
                    app: "dinghuo_baseapi",
                    lang: 'tomcat',
                    group: 'yqm_web',
                    appPort: 33121,
                    artifact: 'target/dinghuo_ec_baseapi-1.0.0-RELEASES.war',
                    git: 'http://gitlab.shixhlocal.com/bizplatform-wms/dinghuo.git',
                    javaOpts: '-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                    portArgs: '-p 33121:8080'
            ],
            [
                    app: "dinghuo_xxl",
                    lang: 'tomcat',
                    group: 'yqm_web',
                    appPort: 33122,
                    artifact: 'target/dinghuo_ec_xxl.war',
                    git: 'http://gitlab.shixhlocal.com/bizplatform-wms/dinghuo.git',
                    javaOpts: '-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dserver.port=8080',
                    portArgs: '-p 33122:8080'
            ],
            //dinghuo_front_admin
            [
                    app: "dinghuo-front-admin",
                    lang: 'nodejsyqm',
                    group: 'vue',
                    appPort: 80,
                    artifact: 'dist',
                    git: 'http://gitlab.shixhlocal.com/bizplatform-wms/dinghuo_front.git',
                    run: 'docker',
                    portArgs: '-p 8080:80',
                    binaryConfig: true
            ],
            // front admin
            [
                    app: "yqm-front-admin",
                    namespace: 'yqm',
                    lang: 'nodejsyqm',
                    group: 'vue',
                    appPort: 80,
                    artifact: 'dist',
                    git: 'http://gitlab.shixhlocal.com/bizplatform/yqm-front-admin.git',
                    run: 'docker',
                    portArgs: '-p 80:80',
                    binaryConfig: true
            ],
            // H5 web
            [
                    app: "wx-web",
                    lang: 'nodejs',
                    group: 'vue',
                    appPort: -1,
                    artifact: 'dist',
                    git: 'http://gitlab.shixhlocal.com/bizplatform/h5-wechat.git'
                    //      binaryConfig: true
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
            ],
            // other
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
