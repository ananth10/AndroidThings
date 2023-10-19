package com.ananth.androidthings.gestures

import android.net.Uri

data class Photo(
    val id: Int,
    val url: Uri,
    val highResUrl: Uri,
    val contentDescription: String
)