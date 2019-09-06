package com.daimler.mbloggerkit.sample

import android.app.Application
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbloggerkit.PrinterConfig
import com.daimler.mbloggerkit.adapter.AndroidLogAdapter
import com.daimler.mbloggerkit.adapter.PersistingLogAdapter
import com.daimler.mbloggerkit.format.SimpleFileLogFormat
import com.daimler.mbloggerkit.obfuscation.*
import com.daimler.mbloggerkit.shake.LogOverlay

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val mailPattern = "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"

        MBLoggerKit.usePrinterConfig(PrinterConfig.Builder()
                .addAdapter(AndroidLogAdapter.Builder()
                        .setLoggingEnabled(BuildConfig.DEBUG)
                        .build())
                .addAdapter(PersistingLogAdapter.Builder(this)
                        .setLoggingEnabled(BuildConfig.DEBUG)
                        .logFormatter(
                                ObfuscatingLogFormatter(
                                        SimpleFileLogFormat(),
                                        Obfuscations.patternObfuscation(mailPattern, "- SECRET -") )
                        )
                        .build())
                .showLogMenuWithShake(this, LogOverlay.Order.CHRONOLOGICAL_DESCENDING)
                .build())
    }
}