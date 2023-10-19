package com.ananth.androidthings.gestures

import android.Manifest
import android.content.ContentUris
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.ananth.androidthings.ui.theme.AndroidThingsTheme
import java.util.Calendar

class GestureActivity:ComponentActivity() {

    val photoViewModel by viewModels<PhotoViewModel>()
    var photoId = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                0
            )
        }
        val yesterdayMillis = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -90)
        }.timeInMillis
        val projections = arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME)
        val selection = "${MediaStore.Images.Media.DATE_TAKEN} >= ?"
        val selectionArgs = arrayOf(yesterdayMillis.toString())
        val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"
        contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projections,
            selection,
            selectionArgs,
            sortOrder

        )?.use { cursor ->
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            val name = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)

            val imageList = mutableListOf<Photo>()
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(name)
                val uri =
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

                imageList.add(Photo(photoId++,uri,uri,name))
            }

            photoViewModel.updatePhotos(imageList)
        }
        setContent {
            AndroidThingsTheme {
                PhotoApp(photos = photoViewModel.photos)
            }
        }
    }
}