package com.daimler.mbloggerkit.obfuscation

class ReplaceMessageObfuscator(private val replacement: String = "") : ObfuscatingLogFormatter.LogObfuscator {
    override fun obfuscate(message: String): String {
        return replacement
    }
}