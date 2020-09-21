package com.everli.uppysample

import android.app.Application
import com.everli.uppy.Uppy
import com.everli.uppy.model.UppyMode

class Application: Application() {

    override fun onCreate() {
        super.onCreate()

        Uppy.init("https://www.example.com/api/", "example-slug", UppyMode.DEFAULT)
    }
}
