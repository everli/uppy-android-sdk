package com.example.uppysample

import android.app.Application
import it.supermercato24.uppy.Uppy

class Application: Application() {

    override fun onCreate() {
        super.onCreate()

        Uppy.init("https://www.example.com")
    }
}
