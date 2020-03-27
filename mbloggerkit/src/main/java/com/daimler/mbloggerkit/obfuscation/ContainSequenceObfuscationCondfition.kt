package com.daimler.mbloggerkit.obfuscation

import com.daimler.mbloggerkit.LogMessage

class ContainSequenceObfuscationCondfition(private val sequence: CharSequence) : ObfuscatingLogFormatter.LogObfuscationCondfition {
    override fun shouldObfuscate(message: LogMessage): Boolean {
        return message.message.contains(sequence)
    }
}