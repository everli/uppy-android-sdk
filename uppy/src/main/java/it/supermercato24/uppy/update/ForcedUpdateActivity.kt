package it.supermercato24.uppy.update

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import it.supermercato24.uppy.R
import it.supermercato24.uppy.downloadmanager.BrowserDownloadManager

class ForcedUpdateActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_DOWNLOAD_URL = "ForcedUpdateActivity.Extra.downloadUrl"

        @JvmStatic
        fun newIntent(context: Context, downloadUrl: String): Intent {
            val intent = Intent(context, ForcedUpdateActivity::class.java)
            val extras = Bundle()

            extras.putString(EXTRA_DOWNLOAD_URL, downloadUrl)
            intent.putExtras(extras)

            return intent
        }
    }

    private var downloadUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forced_update)

        val intent = intent
        downloadUrl = intent.getStringExtra(EXTRA_DOWNLOAD_URL)

        setListeners()
    }

    private fun setListeners() {
        findViewById<Button>(R.id.update).setOnClickListener {
            downloadUrl?.let {
                BrowserDownloadManager(this).startDownload(it)
            } ?: Toast.makeText(this, R.string.update_url_empty, Toast.LENGTH_LONG).show()
        }
    }
}
