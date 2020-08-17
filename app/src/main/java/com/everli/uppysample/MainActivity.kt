package com.everli.uppysample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.everli.uppy.Uppy

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Uppy.checkForUpdates(this, "example-slug", this)
    }
}

