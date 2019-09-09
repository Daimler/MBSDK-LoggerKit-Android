package com.daimler.mbloggerkit.obfuscation

import com.daimler.mbloggerkit.LogMessage
import java.util.regex.Pattern

class MatchPatternObfuscationCondfition(private val regex: String) : ObfuscatingLogFormatter.LogObfuscationCondfition {

    override fun shouldObfuscate(message: LogMessage): Boolean {
        return matches(message.message, Pattern.compile(regex))
    }

    private fun matches(message: String, pattern: Pattern): Boolean {
        return pattern.matcher(message).matches()
    }
}