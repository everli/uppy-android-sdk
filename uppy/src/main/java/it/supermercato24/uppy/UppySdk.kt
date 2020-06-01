package it.supermercato24.uppy

import android.content.Context
import androidx.lifecycle.LifecycleOwner

interface UppySdk {
    fun showUpdates(context: Context, lifecycleOwner: LifecycleOwner)
}
