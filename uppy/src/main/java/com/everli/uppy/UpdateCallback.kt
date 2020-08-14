package com.everli.uppy

import com.everli.uppy.model.UpdateCheck

interface UpdateCallback {

    fun onSuccess(updateCheck: UpdateCheck)

    fun onFailure(t: Throwable)

    fun onNoUpdate(message: String)
}
