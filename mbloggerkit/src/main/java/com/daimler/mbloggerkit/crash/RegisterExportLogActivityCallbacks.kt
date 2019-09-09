package com.daimler.mbloggerkit.crash

import android.app.Activity
import android.app.Application
import android.os.Bundle
import net.hockeyapp.android.CrashManager

class RegisterExportLogActivityCallbacks(private val appId: String?) : Application.ActivityLifecycleCallbacks {

    override fun onActivityPaused(p0: Activity?) {
    }

    override fun onActivityResumed(p0: Activity?) {
        val exportCrashListener = ExportLogCrashListener()
        appId?.let {
            CrashManager.register(p0, it, exportCrashListener)
        } ?: CrashManager.register(p0, exportCrashListener)
    }

    override fun onActivityStarted(p0: Activity?) {
    }

    override fun onActivityDestroyed(p0: Activity?) {
    }

    override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
    }

    override fun onActivityStopped(p0: Activity?) {
    }

    override fun onActivityCreated(p0: Activity?, p1: Bundle?) {
    }
}