package com.ananth.androidthings.uri

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ananth.androidthings.ui.theme.AndroidThingsTheme
import java.io.File
import java.io.FileOutputStream

class UriActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uri = Uri.parse("android.resource://$packageName/drawable/krimit")
        val imageBytes = contentResolver.openInputStream(uri)?.use {
            it.readBytes()
        }
        println("BYTES: ${imageBytes?.size}")

        val file = File(filesDir,"krimit.jpeg")
        FileOutputStream(file).use {
            it.write(imageBytes)
        }
        println("FILE : ${file.toURI()}")
        setContent {
            AndroidThingsTheme {
               val pickImage = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent(),
                   onResult = {contentUri ->
                       println("Content URI: $contentUri")
                   }
               )
                Surface {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = {pickImage.launch("image/*")}) {

                            Text(text = "Pick Image")
                        }
                    }
                }
            }
        }
    }
}