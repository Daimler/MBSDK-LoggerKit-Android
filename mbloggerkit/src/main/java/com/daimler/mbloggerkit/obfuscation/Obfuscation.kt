package com.daimler.mbloggerkit.obfuscation

import com.daimler.mbloggerkit.LogMessage

class Obfuscation(private val obfuscationCondfition: ObfuscatingLogFormatter.LogObfuscationCondfition, private val logObfuscator: ObfuscatingLogFormatter.LogObfuscator) {

    fun shouldObfuscate(logMessage: LogMessage): Boolean {
        return obfuscationCondfition.shouldObfuscate(logMessage)
    }

    fun obfuscate(logMessage: LogMessage): LogMessage {
        return logMessage.copy(message = logObfuscator.obfuscate(logMessage.message))
    }
}