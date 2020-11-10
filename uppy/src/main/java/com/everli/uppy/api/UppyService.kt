package com.everli.uppy.api

import com.everli.uppy.api.requests.CheckForUpdatesRequest
import com.everli.uppy.model.ApiResponse
import com.everli.uppy.model.UpdateCheck
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface UppyService {
    @POST("v2/applications/{slug}/updates/Android")
    fun checkLatestVersion(
        @Path("slug") slug: String,
        @Body checkForUpdatesRequest: CheckForUpdatesRequest
    ): Call<ApiResponse<UpdateCheck>>
}
