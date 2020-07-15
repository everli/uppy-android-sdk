package com.everli.uppy

import android.content.Context
import androidx.lifecycle.LifecycleOwner

interface UppySdk {
    fun checkForUpdates(context: Context, lifecycleOwner: LifecycleOwner)
}
