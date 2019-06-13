def call(to) {
    configFileProvider(
            [configFile(fileId: "html-global-settings", variable: 'body')]) {
                emailext(
                to: to,
                subject: "Running Pipeline: ${currentBuild.fullDisplayName}",
                body: readFile("${body}").trim()
        )
    }
}