package com.everli.uppy

import android.content.Context
import androidx.lifecycle.LifecycleOwner

interface UppySdk {
    fun checkForUpdates(context: Context, slug: String, lifecycleOwner: LifecycleOwner)

    fun checkForUpdates(
        context: Context,
        slug: String,
        lifecycleOwner: LifecycleOwner,
        callback: UpdateCallback
    )
}
