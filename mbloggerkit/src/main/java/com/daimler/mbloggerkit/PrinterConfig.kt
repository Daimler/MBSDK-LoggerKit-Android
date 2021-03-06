package com.daimler.mbloggerkit

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import com.daimler.mbloggerkit.adapter.LogAdapter
import com.daimler.mbloggerkit.crash.*
import com.daimler.mbloggerkit.crash.ExceptionHandler
import com.daimler.mbloggerkit.crash.ExportableCrashListener
import com.daimler.mbloggerkit.format.StackTraceTagStrategy
import com.daimler.mbloggerkit.format.TagStrategy
import com.daimler.mbloggerkit.shake.LogOverlay
import com.daimler.mbloggerkit.shake.ShakeDetector
import com.microsoft.appcenter.crashes.Crashes

class PrinterConfig private constructor(val adapters: Array<LogAdapter>, val tagStrategy: TagStrategy) {

    class Builder {

        private val adapters: MutableMap<String, LogAdapter> = mutableMapOf()

        private var tagStrategy: TagStrategy = StackTraceTagStrategy()

        private var exceptionHandler: ExceptionHandler? = null

        fun tag(tagStrategy: TagStrategy): Builder {
            this.tagStrategy = tagStrategy
            return this
        }

        fun addAdapter(logAdapter: LogAdapter): Builder {
            val adapterKey = logAdapter::class.java.simpleName
            if (adapters.containsKey(adapterKey).not()) {
                adapters[adapterKey] = logAdapter
            }
            return this
        }

        fun showLogMenuWithShake(application: Application, order: LogOverlay.Order): Builder {
            val listener = LogOverlay(order)
            application.registerActivityLifecycleCallbacks(listener)
            initShaking(application, listener)
            return this
        }

        fun addLogToAppCenterCrashes(application: Application, entries: Int = DEFAULT_CRASH_LOG_ENTRIES): Builder {
            val writer = CacheErrorLogFileWriter(application)
            exceptionHandler?.unregister()
            exceptionHandler = WriteErrorLogExceptionHandler(writer).also {
                it.register()
            }
            Crashes.setListener(ExportableCrashListener(entries, writer))
            return this
        }

        private fun initShaking(application: Application, shakeListener: ShakeDetector.ShakeListener, minShakeCount: Int = 2) {
            val sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            val shakeDetector = ShakeDetector(minShakeCount)
            shakeDetector.shakeListener = shakeListener
            sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI)
        }

        fun build(): PrinterConfig {
            return PrinterConfig(adapters.values.toTypedArray(), tagStrategy)
        }

        private companion object {

            private const val DEFAULT_CRASH_LOG_ENTRIES = 100
        }
    }
}