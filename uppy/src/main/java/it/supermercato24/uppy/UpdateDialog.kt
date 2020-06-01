package it.supermercato24.uppy

import android.app.AlertDialog
import android.content.Context

class UpdateDialog(context: Context) : AlertDialog(context) {
    init {
        // TODO
        setTitle("Update Available")
        setMessage("A new version is available, would you like to update?")
        setButton(BUTTON_POSITIVE, "Update!") { dialog, which ->
            TODO("not implemented")
        }
        setButton(BUTTON_NEUTRAL, "Later") { dialog, which ->
            cancel()
        }
        setCancelable(true)
    }
}
