package com.daimler.mbloggerkit.adapter

import android.content.Context
import com.daimler.mbloggerkit.Priority
import com.daimler.mbloggerkit.export.ExportableLog
import com.daimler.mbloggerkit.export.Log
import com.daimler.mbloggerkit.format.LogFormatter
import com.daimler.mbloggerkit.format.SimpleFileLogFormat
import com.daimler.mbloggerkit.strategy.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class PersistingLogAdapter private constructor(
    logFormatter: LogFormatter,
    loggingEnabled: Boolean = false,
    logStrategy: ExportableLog
) : FormattedLogAdapter(logFormatter, logStrategy), ExportableLog {

    override fun log(priority: Priority, tag: String, message: String) {
        logStrategy.log(priority, tag, message)
    }

    override fun loadLog(): Log {
        return (logStrategy as ExportableLog).loadLog()
    }

    override val isLoggable: Boolean = loggingEnabled

    class Builder(private val context: Context) {

        private val DEFAULT_MEMORY_CAPACITY = 200

        private val FILE_EXTENSION_LOG = ".log"

        private val DEFAULT_LOG_DIR = "logs"

        private val DEFAULT_FILE_PATH = File(context.filesDir, DEFAULT_LOG_DIR).absolutePath

        private val DATE_TIME_PATTERN = "yyyy_MM_dd-HH_mm_ss_SSS"

        private val DEFAULT_FILE_NAME = "${SimpleDateFormat(DATE_TIME_PATTERN, Locale.ENGLISH).format(Date(System.currentTimeMillis()))}$FILE_EXTENSION_LOG"

        private var loggingEnabled = false

        private var logFormatter: LogFormatter = SimpleFileLogFormat()

        private var logStrategy: ExportableLog = DiskLogging(DiskLogging.LogFileConfig(DEFAULT_FILE_PATH, DEFAULT_FILE_NAME, SessionsBasedLogFileProvider()))

        fun setLoggingEnabled(enabled: Boolean): Builder {
            loggingEnabled = enabled
            return this
        }

        fun logFormatter(logFormatter: LogFormatter): Builder {
            this.logFormatter = logFormatter
            return this
        }

        fun useMemoryLogging(capacity: Int = DEFAULT_MEMORY_CAPACITY): Builder {
            this.logStrategy = MemoryLogging(capacity)
            return this
        }

        fun useExternalDiskLogging(path: String, fileName: String, logFileProvider: LogFileProvider = SessionsBasedLogFileProvider()): Builder {
            this.logStrategy = ExternalDiskLogging(context, DiskLogging.LogFileConfig(path, fileName, logFileProvider))
            return this
        }

        fun build(): PersistingLogAdapter {
            return PersistingLogAdapter(logFormatter = logFormatter, loggingEnabled = loggingEnabled, logStrategy = logStrategy)
        }
    }
}