package com.everli.uppy.api.requests

import com.squareup.moshi.Json


data class CheckForUpdatesRequest(
    val version: String,
    @Json(name = "device_id") val deviceId: String?
)