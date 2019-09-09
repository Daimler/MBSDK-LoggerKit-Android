package com.daimler.mbloggerkit.crash

import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbloggerkit.Logging
import net.hockeyapp.android.CrashManagerListener

class ExportLogCrashListener : CrashManagerListener(), Logging {

    override fun getDescription(): String = exportedLogString()

    private fun exportedLogString(): String {
        val currentLog = MBLoggerKit.loadCurrentLog()
        val readableLogBuilder = StringBuilder()
        currentLog.forEach {
            readableLogBuilder.appendln(it.message)
        }
        return readableLogBuilder.toString()
    }
}