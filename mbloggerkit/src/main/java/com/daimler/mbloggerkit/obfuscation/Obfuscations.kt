package com.daimler.mbloggerkit.obfuscation

class Obfuscations {
    companion object {

        fun patternObfuscation(regex: String, replacement: String) = Obfuscation(ContainPatternObfuscationCondfition(regex), ReplacePatternObfuscator(regex, replacement))

        fun sequenceObfuscation(sequence: CharSequence, replacement: String) = Obfuscation(ContainSequenceObfuscationCondfition(sequence), ReplaceSequenceObfuscator(sequence.toString(), replacement))

        fun messageObfuscation(condition: ObfuscatingLogFormatter.LogObfuscationCondfition, replacement: String = "") = Obfuscation(condition, ReplaceMessageObfuscator(replacement))
    }
}