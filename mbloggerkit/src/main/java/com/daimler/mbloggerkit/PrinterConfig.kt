package com.daimler.mbloggerkit

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import com.daimler.mbloggerkit.adapter.LogAdapter
import com.daimler.mbloggerkit.crash.RegisterExportLogActivityCallbacks
import com.daimler.mbloggerkit.format.StackTraceTagStrategy
import com.daimler.mbloggerkit.format.TagStrategy
import com.daimler.mbloggerkit.shake.LogOverlay
import com.daimler.mbloggerkit.shake.ShakeDetector

class PrinterConfig private constructor(val adapters: Array<LogAdapter>, val tagStrategy: TagStrategy) {

    class Builder {

        private val adapters: MutableMap<String, LogAdapter> = mutableMapOf()

        private var tagStrategy: TagStrategy = StackTraceTagStrategy()

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

        fun addLogToHockeyUpload(context: Application, appId: String? = null): Builder {
            context.registerActivityLifecycleCallbacks(RegisterExportLogActivityCallbacks(appId))
            return this
        }

        fun showLogMenuWithShake(application: Application, order: LogOverlay.Order): Builder {
            val listener = LogOverlay(order)
            application.registerActivityLifecycleCallbacks(listener)
            initShaking(application, listener)
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
    }
}