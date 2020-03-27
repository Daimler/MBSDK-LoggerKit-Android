package com.daimler.mbloggerkit

import android.support.annotation.WorkerThread
import com.daimler.mbloggerkit.export.Log

object MBLoggerKit {

    private var printer: LogPrinter = LogPrinter(PrinterConfig.Builder().build())

    fun usePrinterConfig(printerConfig: PrinterConfig) {
        printer = LogPrinter(printerConfig)
    }

    fun v(message: String, tag: String? = null, throwable: Throwable? = null) {
        printer.log(Priority.VERBOSE, tag, message, throwable)
    }

    fun d(message: String, tag: String? = null, throwable: Throwable? = null) {
        printer.log(Priority.DEBUG, tag, message, throwable)
    }

    fun i(message: String, tag: String? = null, throwable: Throwable? = null) {
        printer.log(Priority.INFO, tag, message, throwable)
    }

    fun w(message: String, tag: String? = null, throwable: Throwable? = null) {
        printer.log(Priority.WARN, tag, message, throwable)
    }

    fun e(message: String, tag: String? = null, throwable: Throwable? = null) {
        printer.log(Priority.ERROR, tag, message, throwable)
    }

    fun wtf(message: String, tag: String? = null, throwable: Throwable? = null) {
        printer.log(Priority.WTF, tag, message, throwable)
    }

    fun log(priority: Priority, message: String, tag: String? = null, throwable: Throwable? = null) {
        printer.log(priority, tag, message, throwable)
    }

    @WorkerThread
    fun loadCurrentLog(): Log {
        return printer.loadCurrentLog()
    }
}