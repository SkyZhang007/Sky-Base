package com.sky.lux.component.keyboard.keyboard.device

class LogFormatter {

    private val stringBuilder = StringBuilder()
    private val defaultKey = "                                                                                                    "
    private var keyLength: Int = 0

    constructor(keyLength: Int) {
        this.keyLength = keyLength
    }

    companion object {
        fun setUp(int: Int = 50): LogFormatter {
            return LogFormatter(int)
        }
    }

    fun addContent(key: String? = "", value: String?): LogFormatter {
        if (key?.isEmpty() == true) {
            stringBuilder.append("$value \n")
        } else {
            if (key?.length ?: 0 < keyLength) {
                stringBuilder.append("$key${defaultKey.subSequence(0, keyLength - (key?.length ?: 0))} = $value \n")
            } else {
                stringBuilder.append("$key = $value \n")
            }
        }
        return this
    }

    fun log(tag: String?) {
        stringBuilder.clear()
    }
}