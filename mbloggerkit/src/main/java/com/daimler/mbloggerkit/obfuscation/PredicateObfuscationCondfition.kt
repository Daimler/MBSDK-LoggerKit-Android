package com.daimler.mbloggerkit.obfuscation

import com.daimler.mbloggerkit.LogMessage

class PredicateObfuscationCondfition(private val predicate: (LogMessage) -> Boolean) : ObfuscatingLogFormatter.LogObfuscationCondfition {
    override fun shouldObfuscate(message: LogMessage): Boolean {
        return predicate.invoke(message)
    }
}