package com.ananth.androidthings.testing.data.remote.responses



data class ImageResponse(
    val hits:List<ImageResult>,
    val total:Int,
    val totalHits:Int
)
