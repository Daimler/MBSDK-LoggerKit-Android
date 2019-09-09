package com.daimler.mbloggerkit.export

import android.content.Context
import android.content.Intent
import android.support.v4.content.FileProvider
import java.io.File

class Log : Iterable<LogEntry> {

    private val logEntries = mutableListOf<LogEntry>()

    internal fun addToLog(entry: String) {
        logEntries.add(LogEntry(entry))
    }

    override fun iterator(): Iterator<LogEntry> {
        return logEntries.iterator()
    }

    internal fun entries() = logEntries
}

fun Log.shareAsText(context: Context) {
    val sharedLog = StringBuilder()
    this.forEach {
        sharedLog.appendln(it.message)
    }
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, sharedLog.toString())
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share Log"))
}

fun Log.shareAsFile(context: Context, fileName: String) {
    val sharedLogFile = File(context.cacheDir, fileName)
    if (sharedLogFile.exists().not()) {
        if (sharedLogFile.createNewFile()) {
            shareLogFile(context, sharedLogFile, this)
        }
    } else {
        if (sharedLogFile.isDirectory.not()) {
            shareLogFile(context, sharedLogFile, this)
        }
    }
}

private fun shareLogFile(context: Context, logFile: File, log: Log) {
    val fileProviderSuffix = ".provider" // same as used in Manifest
    val authority = "${context.packageName}$fileProviderSuffix"
    val sharedLog = StringBuilder()
    log.forEach {
        sharedLog.appendln(it.message)
    }
    logFile.writeText(sharedLog.toString())
    val shareUri = FileProvider.getUriForFile(context, authority, logFile)
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_STREAM, shareUri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        type = "plain/*"
    }
    context.startActivity(shareIntent)
}