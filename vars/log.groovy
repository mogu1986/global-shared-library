def debug(String message) {
    wrap([$class: 'AnsiColorBuildWrapper', 'colorMapName': 'XTerm']) {
        echo "debug: ${message}"
    }
}