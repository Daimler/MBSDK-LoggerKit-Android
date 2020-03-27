package com.daimler.mbloggerkit.format

import com.daimler.mbloggerkit.LogMessage
import java.text.SimpleDateFormat
import java.util.*

class SimpleFileLogFormat : LogFormatter {

    private val DATE_TIME_PATTERN = "yy-MM-dd HH:mm:ss.SSS"

    val dateFormatter = SimpleDateFormat(DATE_TIME_PATTERN, Locale.ENGLISH)

    override fun format(logMessage: LogMessage): String {
        return "${dateFormatter.format(Date(logMessage.timestamp))} | ${logMessage.priority.name} | ${logMessage.message}${errorLogInfoIfAvailable(logMessage.throwable)}"
    }

    private fun errorLogInfoIfAvailable(throwable: Throwable?): String {
        return throwable?.let {
            " | ${it.message}"
        } ?: ""
    }
}