def call(sonarExec) {
    def sonarHome = tool name: 'SonarQube Scanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
    withSonarQubeEnv('sonar'){
        sonarExec(sonarHome)
    }
}