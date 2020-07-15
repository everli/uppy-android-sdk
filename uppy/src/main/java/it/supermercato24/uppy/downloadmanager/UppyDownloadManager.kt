package it.supermercato24.uppy.downloadmanager

import android.app.DownloadManager
import android.app.DownloadManager.Request
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import it.supermercato24.uppy.R
import java.io.File

class UppyDownloadManager(private val context: Context) {
    private var mgr: DownloadManager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
    private var downloadId = -1L
    private val apkFilename: String = "latest.apk"
    private var apkUri: Uri = Uri.EMPTY

    var onComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(ctxt: Context, intent: Intent) {

            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                apkFilename
            )
            if (file.exists()) {
                file.delete()
            }

            val install = Intent(Intent.ACTION_VIEW)
            install.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

            apkUri = FileProvider.getUriForFile(
                context, context.applicationContext
                    .packageName + ".provider", file
            )
            install.setDataAndType(apkUri, mgr.getMimeTypeForDownloadedFile(downloadId))
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            context.startActivity(install)

            context.unregisterReceiver(this)
        }
    }

    init {
        context.registerReceiver(
            onComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
    }

    fun startDownload(url: String) {
        val uri = Uri.parse(url)
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdirs()

        downloadId = mgr.enqueue(
            Request(uri)
                .setAllowedNetworkTypes(Request.NETWORK_WIFI or Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(context.getString(R.string.downloading_updates))
                .setDescription(context.getString(R.string.downloading_updates))
                .setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    apkFilename
                )
        )
    }
}
