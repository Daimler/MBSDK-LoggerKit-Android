package com.daimler.mbloggerkit.obfuscation

class ReplacePatternObfuscator(private val regex: String, private val replacement: String) : ObfuscatingLogFormatter.LogObfuscator {
    override fun obfuscate(message: String): String {
        return message.replace(Regex(regex), replacement)
    }
}