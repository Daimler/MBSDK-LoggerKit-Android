package com.daimler.mbloggerkit.adapter

import com.daimler.mbloggerkit.format.LogFormatter
import com.daimler.mbloggerkit.format.SimpleLogFormatter
import com.daimler.mbloggerkit.strategy.LogCatLogging

class AndroidLogAdapter private constructor(logFormatter: LogFormatter, loggingEnabled: Boolean = false) : FormattedLogAdapter(logFormatter, LogCatLogging()) {

    override val isLoggable: Boolean = loggingEnabled

    class Builder {

        private var loggingEnabled = false

        private var logFormatter: LogFormatter = SimpleLogFormatter()

        fun setLoggingEnabled(enabled: Boolean): Builder {
            loggingEnabled = enabled
            return this
        }

        fun logFormatter(logFormatter: LogFormatter): Builder {
            this.logFormatter = logFormatter
            return this
        }

        fun build(): AndroidLogAdapter {
            return AndroidLogAdapter(logFormatter, loggingEnabled = loggingEnabled)
        }
    }
}