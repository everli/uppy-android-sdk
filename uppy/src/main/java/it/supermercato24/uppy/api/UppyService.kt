package it.supermercato24.uppy.api

import it.supermercato24.uppy.model.ApiResponse
import it.supermercato24.uppy.model.UpdateCheck
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UppyService {
    @GET("v1/applications/shopper-app/updates/Android/{version}")
    fun checkLatestVersion(@Path("version") currentAppVersion: String): Call<ApiResponse<UpdateCheck>>
}
