package it.supermercato24.uppy

import com.squareup.moshi.Json

data class UpdateCheck(
    @Json(name = "updates_available") val updatesAvailable: Boolean,
    @Json(name = "forced") val forced: Boolean,
    @Json(name = "download_url") val downloadUrl: String
)
