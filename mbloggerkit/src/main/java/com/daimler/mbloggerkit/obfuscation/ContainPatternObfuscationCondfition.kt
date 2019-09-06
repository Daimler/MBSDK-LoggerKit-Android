package com.daimler.mbloggerkit.obfuscation

import com.daimler.mbloggerkit.LogMessage

class ContainPatternObfuscationCondfition(private val regex: String) : ObfuscatingLogFormatter.LogObfuscationCondfition {
    override fun shouldObfuscate(message: LogMessage): Boolean {
        return containsPattern(message.message, Regex(pattern = regex))
    }

    private fun containsPattern(message: String, regex: Regex): Boolean {
        return message.contains(regex)
    }
}