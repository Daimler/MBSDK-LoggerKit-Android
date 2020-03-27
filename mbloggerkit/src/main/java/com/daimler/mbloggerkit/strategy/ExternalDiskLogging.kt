package com.daimler.mbloggerkit.strategy

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import com.daimler.mbloggerkit.Priority

internal class ExternalDiskLogging(private val context: Context, fileConfig: DiskLogging.LogFileConfig) : DiskLogging(fileConfig) {

    override fun log(priority: Priority, tag: String, message: String) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            super.log(priority, tag, message)
        }
    }
}