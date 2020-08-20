package com.everli.uppy.api

import android.os.Build
import okhttp3.Interceptor
import okhttp3.Response

class UserAgentInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("User-Agent", getUserAgent())
            .build()
        return chain.proceed(request)
    }

    /**
     * Returns the User-Agent in the form Android/<release_no>/<manufacturer>/<device_model>
     * Example: "Android/9/google/pixel 3a"
     */
    private fun getUserAgent() =
        "Android/" + Build.VERSION.RELEASE + "/" + Build.MANUFACTURER + "/" + Build.MODEL

}
