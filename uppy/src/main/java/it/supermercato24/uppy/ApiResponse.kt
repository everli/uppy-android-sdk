package it.supermercato24.uppy

import com.squareup.moshi.Json

data class ApiResponse<T>(@Json(name = "data") val data: T)
