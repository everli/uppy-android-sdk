package com.everli.uppy.model

import com.squareup.moshi.Json

data class UpdateCheck(
    @Json(name = "forced") val forced: Boolean,
    @Json(name = "version") val version: String,
    @Json(name = "download_url") val downloadUrl: String
)
