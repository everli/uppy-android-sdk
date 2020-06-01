package it.supermercato24.uppy

import com.squareup.moshi.Json

data class UpdateCheck(
    @field:Json(name = "updates_available") val updatesAvailable: Boolean,
    @field:Json(name = "forced") val forced: Boolean,
    @field:Json(name = "download_url") val downloadUrl: String
)
