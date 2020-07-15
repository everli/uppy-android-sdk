package com.everli.uppy.api

import com.everli.uppy.model.ApiResponse
import com.everli.uppy.model.UpdateCheck
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UppyService {
    @GET("v1/applications/shopper-app/updates/Android/{version}")
    fun checkLatestVersion(@Path("version") currentAppVersion: String): Call<ApiResponse<UpdateCheck>>
}
