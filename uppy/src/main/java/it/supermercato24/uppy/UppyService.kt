package it.supermercato24.uppy

import retrofit2.Callback
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

interface UppyService {
    @GET("/v1/updates/android/{version}")
    fun checkLatestVersion(
        @Path("version") currentAppVersion: String?,
        callback: Callback<ApiResponse<UpdateCheck>>
    )
}
