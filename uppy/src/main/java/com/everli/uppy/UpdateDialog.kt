package com.everli.uppy

import android.app.AlertDialog
import android.content.Context
import it.everli.uppy.R

class UpdateDialog(
    context: Context,
    private val downloadUrl: String
) : AlertDialog(context) {

    init {
        setTitle(R.string.uppy_update_dialog_title)
        setMessage(context.getString(R.string.uppy_update_dialog_message))

        setButton(BUTTON_POSITIVE, context.getString(R.string.uppy_update_button)) { _, _ ->
            BrowserDownloadManager(context).startDownload(downloadUrl)
        }

        setButton(BUTTON_NEUTRAL, context.getString(R.string.uppy_update_dialog_later)) { _, _ ->
            cancel()
        }

        setCancelable(false)
    }
}
