package com.everli.uppy

import android.app.AlertDialog
import android.content.Context
import it.everli.uppy.R

class UpdateDialog(
    context: Context,
    private val downloadUrl: String
) : AlertDialog(context) {

    init {
        setTitle(R.string.dialog_update_available)
        setMessage(context.getString(R.string.dialog_new_version_available))

        setButton(BUTTON_POSITIVE, context.getString(R.string.update)) { _, _ ->
            BrowserDownloadManager(context).startDownload(downloadUrl)
        }

        setButton(BUTTON_NEUTRAL, context.getString(R.string.dialog_later)) { _, _ ->
            cancel()
        }

        setCancelable(false)
    }
}
