package it.supermercato24.uppy

import android.app.AlertDialog
import android.content.Context
import it.supermercato24.uppy.downloadmanager.BrowserDownloadManager

class UpdateDialog(context: Context, private val downloadUrl: String) : AlertDialog(context) {
    init {
        setTitle(R.string.update_available)
        setMessage(context.getString(R.string.a_new_version_available))
        setButton(BUTTON_POSITIVE, context.getString(R.string.update_2)) { _, _ ->
            BrowserDownloadManager(context).startDownload(downloadUrl)
        }
        setButton(BUTTON_NEUTRAL, context.getString(R.string.later)) { _, _ ->
            cancel()
        }
        setCancelable(false)
    }
}
