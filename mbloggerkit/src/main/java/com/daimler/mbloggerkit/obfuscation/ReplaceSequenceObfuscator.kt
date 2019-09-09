package com.daimler.mbloggerkit.obfuscation

class ReplaceSequenceObfuscator(private val oldValue: String, private val newValue: String) : ObfuscatingLogFormatter.LogObfuscator {
    override fun obfuscate(message: String): String {
        return message.replace(oldValue, newValue)
    }
}