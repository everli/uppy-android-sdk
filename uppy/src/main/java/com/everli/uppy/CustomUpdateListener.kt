package com.everli.uppy

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.everli.uppy.model.UpdateCheck
import java.lang.ref.WeakReference

class CustomUpdateListener(
    private val updateCheck: UpdateCheck,
    lifecycle: Lifecycle,
    private val callback: UpdateCallback
) : UpdateListener, LifecycleObserver {

    private val lifecycleRef: WeakReference<Lifecycle> = WeakReference(lifecycle)

    override fun showUpdates() {
        val lifecycle = lifecycleRef.get()
        if (lifecycle?.currentState?.isAtLeast(Lifecycle.State.RESUMED) == true) {
            callback.onSuccess(updateCheck)
        } else {
            lifecycle?.addObserver(this)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun showUpdatesDeferred() {
        callback.onSuccess(updateCheck)
        lifecycleRef.get()?.removeObserver(this)
    }
}
