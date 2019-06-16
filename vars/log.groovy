def debug(String message) {
    ansiColor('xterm') {
        echo "debug: ${message}"
    }
}