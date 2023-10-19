package com.ananth.androidthings.gestures


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PhotoViewModel : ViewModel() {
    var photos by mutableStateOf(emptyList<Photo>())
        private set

    fun updatePhotos(photos: List<Photo>) {
        this.photos = photos
    }
}