def call(Map map) {

    def flag = false

    if (map.containsKey('binaryConfig') && map.get('binaryConfig') == true) {
        flag = true
    }
    log.debug("项目需要二进制配置文件： ${flag}")
    return flag
}