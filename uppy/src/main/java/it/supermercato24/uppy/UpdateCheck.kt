package it.supermercato24.uppy

data class UpdateCheck(
    val updatesAvailable: Boolean,
    val forced: Boolean,
    val downloadUrl: String
)
