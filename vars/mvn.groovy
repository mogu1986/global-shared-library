def call(mvnExec) {
    configFileProvider(
            [configFile(fileId: "${BUILD_BRANCH}-maven-global-settings", variable: 'MAVEN_SETTINGS_ENV')]) {
            docker.image('maven:3-jdk-8-alpine').inside('-v /root/.m2:/root/.m2 -v /root/.sonar:/root/.sonar') {
                log.debug("MAVEN_SETTINGS: ${MAVEN_SETTINGS_ENV}")
                mvnExec("${MAVEN_SETTINGS_ENV}")
            }
    }
}