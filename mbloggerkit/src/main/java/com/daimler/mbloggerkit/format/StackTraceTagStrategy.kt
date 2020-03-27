package com.daimler.mbloggerkit.format

import android.os.Build
import com.daimler.mbloggerkit.Logging

class StackTraceTagStrategy : TagStrategy {

    override fun createTag(tag: String?): String {
        return tag?.let {
            it
        } ?: tagFromStackTrace(Throwable().stackTrace)
    }

    private fun tagFromStackTrace(stacktrace: Array<StackTraceElement>): String {
        return if (stacktrace.size < CALL_STACK_INDEX) {
            throw IllegalStateException("Stacktrace has not enough elements")
        } else {
            tagFromStacktraceElement(stacktrace, CALL_STACK_INDEX)
        }
    }

    private fun tagFromStacktraceElement(stacktrace: Array<StackTraceElement>, index: Int): String {
        val fullClassName = stacktrace[index].className
        var tag = fullClassName.substring(fullClassName.lastIndexOf('.') + 1)
        if (loggedFromLoggingExtension(tag)) {
            tag = tagFromStacktraceElement(stacktrace, index + 1)
        }
        return if (tag.length <= MAX_TAG_LENGTH || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tag
        } else {
            tag.substring(0, MAX_TAG_LENGTH)
        }
    }

    private fun loggedFromLoggingExtension(logTag: String): Boolean {
        return "${Logging::class.java.simpleName}$KOTLIN_CLASS_EXTENSION_KT" == logTag
    }

    private companion object {
        /**
         * This index is based on current implementation of MBLoggerKit.
         */
        private const val CALL_STACK_INDEX = 4

        private const val KOTLIN_CLASS_EXTENSION_KT = "Kt"

        /**
         * Maximum length of TAGs, required on SDK_INT < 24.
         */
        private const val MAX_TAG_LENGTH = 23
    }
}