def call(mvnExec) {
    configFileProvider(
            [configFile(fileId: "maven-global-settings", variable: 'MAVEN_SETTINGS_ENV')]) {
            docker.image('maven:3-jdk-8-alpine').inside('-v /root/.m2:/root/.m2') {
                log.debug("MAVEN_SETTINGS: ${MAVEN_SETTINGS_ENV}")
                mvnExec("${MAVEN_SETTINGS_ENV}")
            }
    }
}