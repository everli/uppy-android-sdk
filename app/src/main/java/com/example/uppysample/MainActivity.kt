package com.example.uppysample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import it.supermercato24.uppy.Uppy

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Uppy.isLatestVersion()
    }
}
