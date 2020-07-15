package com.everli.uppy

import android.Manifest
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
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
            .checkLatestVersion(getCurrentAppVersion(context))
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

    private fun getCurrentAppVersion(context: Context): String {
        val pm: PackageManager = context.packageManager
        var pInfo: PackageInfo? = null
        try {
            pInfo = pm.getPackageInfo(context.packageName, 0)
        } catch (e1: PackageManager.NameNotFoundException) {
            Log.e("Uppy", "Can't find version name", e1)

        }
        val currentVersion = pInfo!!.versionName
        return currentVersion
    }

    fun checkStoragePermission(context: Context): Boolean {
        val check =
            ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (check != PackageManager.PERMISSION_GRANTED) {
            return false
        }

        return true
    }

}
