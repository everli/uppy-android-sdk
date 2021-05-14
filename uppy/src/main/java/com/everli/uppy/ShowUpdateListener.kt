package com.everli.uppy

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.everli.uppy.model.UpdateCheck
import com.everli.uppy.update.ForcedUpdateActivity
import java.lang.ref.WeakReference

class ShowUpdateListener(
    private val updateCheck: UpdateCheck,
    lifecycle: Lifecycle,
    context: Context
) : UpdateListener, LifecycleObserver {

    private val lifecycleRef: WeakReference<Lifecycle> = WeakReference(lifecycle)
    private val contextRef: WeakReference<Context> = WeakReference(context)

    override fun showUpdates() {
        val lifecycle = lifecycleRef.get()

        if (lifecycle?.currentState?.isAtLeast(Lifecycle.State.RESUMED) == true) {
            showUpdateResult()
        } else {
            lifecycle?.addObserver(this)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun showUpdatesDeferred() {
        showUpdateResult()
        lifecycleRef.get()?.removeObserver(this)
    }

    private fun showUpdateResult() = contextRef.get()?.let {
        if (!updateCheck.forced) {
            UpdateDialog(
                it,
                updateCheck.downloadUrl
            ).show()
        } else {
            val intent = ForcedUpdateActivity.newIntent(
                it,
                updateCheck.downloadUrl
            )

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

            it.startActivity(intent)
        }
    }
}
