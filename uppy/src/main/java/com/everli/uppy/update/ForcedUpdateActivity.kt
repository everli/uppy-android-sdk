package com.everli.uppy.update

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.everli.uppy.BrowserDownloadManager
import it.everli.uppy.R
import it.everli.uppy.databinding.ActivityForcedUpdateBinding

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

    private lateinit var binding: ActivityForcedUpdateBinding

    private var downloadUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForcedUpdateBinding.inflate(layoutInflater)

        setContentView(binding.root)

        downloadUrl = intent.getStringExtra(EXTRA_DOWNLOAD_URL)

        setListeners()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun setListeners() {
        findViewById<Button>(R.id.update).setOnClickListener {
            downloadUrl?.let {
                BrowserDownloadManager(this).startDownload(it)
            } ?: Toast.makeText(this, R.string.uppy_forced_update_url_empty, Toast.LENGTH_LONG).show()
        }
    }
}
