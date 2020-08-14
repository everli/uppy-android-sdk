package com.everli.uppy

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.everli.uppy.api.UppyService
import com.everli.uppy.api.UserAgentInterceptor
import com.everli.uppy.model.ApiResponse
import com.everli.uppy.model.UpdateCheck
import com.everli.uppy.model.UppyMode
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import it.everli.uppy.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Uppy : UppySdk {
    private const val HTTP_CODE = 404

    private lateinit var serverUrl: String

    private lateinit var client: OkHttpClient
    private lateinit var retrofit: Retrofit
    private lateinit var uppyService: UppyService
    private lateinit var uppyMode: UppyMode

    fun init(serverUrl: String, mode: UppyMode) {
        Uppy.serverUrl = serverUrl
        uppyMode = mode

        val logging = HttpLoggingInterceptor()
        val level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        logging.level = level

        client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(UserAgentInterceptor())
            .build()

        retrofit = Retrofit.Builder()
            .client(client)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .baseUrl(serverUrl)
            .build()

        uppyService = retrofit.create(
            UppyService::class.java
        )
    }

    override fun checkForUpdates(context: Context, lifecycleOwner: LifecycleOwner) {
        uppyService
            .checkLatestVersion(getCurrentAppPackage(context), getCurrentAppVersion(context))
            .enqueue(object : Callback<ApiResponse<UpdateCheck>> {
                override fun onFailure(call: Call<ApiResponse<UpdateCheck>>, t: Throwable) {
                    Log.e("Uppy", "Can't fetch updates", t)
                }

                override fun onResponse(
                    call: Call<ApiResponse<UpdateCheck>>,
                    response: Response<ApiResponse<UpdateCheck>>
                ) {
                    val updateCheck = response.body()?.data
                    if (response.isSuccessful) {
                        updateCheck?.let {
                            ShowUpdateListener(
                                it,
                                lifecycleOwner.lifecycle,
                                context
                            ).showUpdates()
                        }
                    }
                }
            })
    }

    override fun checkForUpdates(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        callback: UpdateCallback
    ) {
        uppyService
            .checkLatestVersion(getCurrentAppPackage(context), getCurrentAppVersion(context))
            .enqueue(object : Callback<ApiResponse<UpdateCheck>> {
                override fun onFailure(call: Call<ApiResponse<UpdateCheck>>, t: Throwable) {
                    Log.e("Uppy", "Can't fetch updates", t)

                    if (lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                        callback.onFailure(t)
                    }
                }

                override fun onResponse(
                    call: Call<ApiResponse<UpdateCheck>>,
                    response: Response<ApiResponse<UpdateCheck>>
                ) {
                    val updateCheck = response.body()?.data
                    if (response.isSuccessful) {
                        updateCheck?.let {
                            CustomUpdateListener(
                                it,
                                lifecycleOwner.lifecycle,
                                callback
                            ).showUpdates()
                        }
                    } else if (response.code() == HTTP_CODE) {
                        if (lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            callback.onNoUpdate("No updates available")
                        }
                    }
                }
            })
    }

    private fun getCurrentAppVersion(context: Context): String {
        val pm: PackageManager = context.packageManager
        var pInfo: PackageInfo? = null

        try {
            pInfo = pm.getPackageInfo(context.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e("Uppy", "Can't find package name", e)
        }

        return pInfo?.versionName
            ?: throw PackageManager.NameNotFoundException("Can't find version name")
    }

    private fun getCurrentAppPackage(context: Context): String {
        val pm: PackageManager = context.packageManager
        var pInfo: PackageInfo? = null

        try {
            pInfo = pm.getPackageInfo(context.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e("Uppy", "Can't find package name", e)
        }

        return pInfo?.packageName
            ?: throw PackageManager.NameNotFoundException("Can't find package name")
    }
}
