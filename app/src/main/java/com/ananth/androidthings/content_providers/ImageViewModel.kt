package com.ananth.androidthings.content_providers

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ImageViewModel : ViewModel() {

    var images by mutableStateOf(emptyList<Image>())
        private set


    fun updateImages(imageList: List<Image>) {
        this.images = imageList
    }
}