package it.supermercato24.uppy

import retrofit2.Callback
import retrofit2.http.Body
import retrofit2.http.GET

interface UppyService {
    @GET("/v1/update-check")
    fun checkLatestVersion(
        @Body currentVersion: String?,
        callback: Callback<ApiResponse<UpdateCheck>>
    )
}
