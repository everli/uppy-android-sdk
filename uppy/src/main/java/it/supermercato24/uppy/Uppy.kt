package it.supermercato24.uppy

import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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

    fun init(serverUrl: String) {
        this.serverUrl = serverUrl

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
        
        uppyService = retrofit.create(UppyService::class.java)
    }

    override fun showUpdates(context: Context, lifecycleOwner: LifecycleOwner) {
        uppyService.checkLatestVersion(context.packageName).enqueue(
            object : Callback<ApiResponse<UpdateCheck>> {
                override fun onFailure(call: Call<ApiResponse<UpdateCheck>>, t: Throwable) {
                    Log.e("Uppy", "Can't fetch updates", t)
                }

                override fun onResponse(
                    call: Call<ApiResponse<UpdateCheck>>,
                    response: Response<ApiResponse<UpdateCheck>>
                ) {
                    val updateCheck = response.body()?.data
                    if (response.isSuccessful && updateCheck?.updatesAvailable == true) {
                        if (lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            showUpdateResult(updateCheck, context)
                        } else {
                            lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
                                @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
                                fun showUpdates() {
                                    showUpdateResult(updateCheck, context)
                                }
                            })
                        }
                    }
                }
            })
    }

    private fun showUpdateResult(
        updateCheck: UpdateCheck,
        context: Context
    ) {
        if (!updateCheck.forced) {
            UpdateDialog(context).show()
        } else {
            TODO("show forced update activity")
        }
    }


}
