package com.everli.uppy

import android.app.AlertDialog
import android.content.Context
import it.everli.uppy.R

class UpdateDialog(
    context: Context,
    private val downloadUrl: String,
    private val title: Int?,
    private val message: Int?
) : AlertDialog(context) {

    init {
        title?.let {
            setTitle(it)
        } ?: setTitle(R.string.dialog_update_available)

        message?.let {
            setMessage(context.getString(it))
        } ?: setMessage(context.getString(R.string.dialog_new_version_available))

        setButton(BUTTON_POSITIVE, context.getString(R.string.update)) { _, _ ->
            BrowserDownloadManager(context).startDownload(downloadUrl)
        }

        setButton(BUTTON_NEUTRAL, context.getString(R.string.dialog_later)) { _, _ ->
            cancel()
        }

        setCancelable(false)
    }
}
