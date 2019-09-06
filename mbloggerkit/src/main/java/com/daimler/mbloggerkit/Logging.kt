package com.daimler.mbloggerkit

import android.content.Context
import com.daimler.mbloggerkit.export.shareAsFile
import com.daimler.mbloggerkit.export.shareAsText

interface Logging

fun Logging.verbose(message: String, tag: String? = null, throwable: Throwable? = null) {
    MBLoggerKit.v(message, tag, throwable)
}

fun Logging.debug(message: String, tag: String? = null, throwable: Throwable? = null) {
    MBLoggerKit.d(message, tag, throwable)
}

fun Logging.info(message: String, tag: String? = null, throwable: Throwable? = null) {
    MBLoggerKit.i(message, tag, throwable)
}

fun Logging.warn(message: String, tag: String? = null, throwable: Throwable? = null) {
    MBLoggerKit.w(message, tag, throwable)
}

fun Logging.error(message: String, tag: String? = null, throwable: Throwable? = null) {
    MBLoggerKit.e(message, tag, throwable)
}

fun Logging.wtf(message: String, tag: String? = null, throwable: Throwable? = null) {
    MBLoggerKit.wtf(message, tag, throwable)
}

fun Logging.shareLogAsFile(context: Context, fileName: String) {
    MBLoggerKit.loadCurrentLog().shareAsFile(context, fileName)
}

fun Logging.shareLogAsText(context: Context) {
    MBLoggerKit.loadCurrentLog().shareAsText(context)
}
