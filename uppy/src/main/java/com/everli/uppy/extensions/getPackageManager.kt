package com.everli.uppy.extensions

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Log


fun PackageManager.getCurrentAppVersion(context: Context): String {
    val pm = this
    var pInfo: PackageInfo? = null

    try {
        pInfo = pm.getPackageInfo(context.packageName, 0)
    } catch (e: PackageManager.NameNotFoundException) {
        Log.e("Uppy", "Can't find package name", e)
    }

    return pInfo?.versionName
        ?: throw PackageManager.NameNotFoundException("Can't find version name")
}

fun PackageManager.getCurrentAppPackage(context: Context): String {
    val pm: PackageManager = this
    var pInfo: PackageInfo? = null

    try {
        pInfo = pm.getPackageInfo(context.packageName, 0)
    } catch (e: PackageManager.NameNotFoundException) {
        Log.e("Uppy", "Can't find package name", e)
    }

    return pInfo?.packageName
        ?: throw PackageManager.NameNotFoundException("Can't find package name")
}
