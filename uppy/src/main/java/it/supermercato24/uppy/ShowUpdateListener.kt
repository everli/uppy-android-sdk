package it.supermercato24.uppy

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import java.lang.ref.WeakReference

class ShowUpdateListener(
    private val updateCheck: UpdateCheck,
    lifecycle: Lifecycle,
    context: Context
) : LifecycleObserver {

    private val lifecycleRef: WeakReference<Lifecycle> = WeakReference(lifecycle)
    private val contextRef: WeakReference<Context> = WeakReference(context)

    fun showUpdates() {
        val lifecycle = lifecycleRef.get()
        if (lifecycle?.currentState?.isAtLeast(Lifecycle.State.RESUMED) == true) {
            showUpdateResult()
        } else {
            lifecycle?.addObserver(this)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun showUpdatesDeferred() {
        showUpdateResult()
        lifecycleRef.get()?.removeObserver(this)
    }

    private fun showUpdateResult() {
        val context = contextRef.get()
        context?.let {
            if (!updateCheck.forced) {
                UpdateDialog(context).show()
            } else {
                TODO("show forced update activity")
            }
        }
    }
}
