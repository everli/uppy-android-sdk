package it.supermercato24.uppy

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

    // Example: Android/9/google/pixel 3a
    fun getUserAgent() =
        "Android/" + Build.VERSION.RELEASE + "/" + Build.MANUFACTURER + "/" + Build.MODEL

}
