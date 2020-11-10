package com.everli.uppysample

import android.app.Application
import com.everli.uppy.Uppy

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        Uppy.init("https://www.example.com/api/", "example-slug")
        // if you want to track installations, provide a unique device id
        //  Uppy.init("https://www.example.com/api/", "example-slug", "your-device-id")
    }
}
