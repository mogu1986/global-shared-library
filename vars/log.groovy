def debug(String message) {
    ansiColor('xterm') {
        echo "debug: ${message}"
    }
}

def error(String message) {
    ansiColor('xterm') {
        echo "error: ${message}"
    }
}