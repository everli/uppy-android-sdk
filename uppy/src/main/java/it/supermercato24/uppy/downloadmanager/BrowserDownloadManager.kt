package it.supermercato24.uppy.downloadmanager

import android.content.Context
import android.content.Intent
import android.net.Uri

class BrowserDownloadManager(private val context: Context) {

    fun startDownload(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(browserIntent)
    }
}
