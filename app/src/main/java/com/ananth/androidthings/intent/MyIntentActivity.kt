package com.ananth.androidthings.intent

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ananth.androidthings.ui.theme.AndroidThingsTheme

class MyIntentActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidThingsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        Alignment.CenterHorizontally
                    ) {
                        Button(onClick = {
//                            explicitIntent()
                            implicitIntent()

                        }) {
                            Text(
                                text = "Click",
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

    }


    //Explicit intent is to start the external app by mentioning package name
    //we are explicitly mentioned what we need to with intent, this intent will send to only one specific app
    private fun explicitIntent() {
        Intent(Intent.ACTION_MAIN).also {
            it.`package` = "com.google.android.youtube"
            if (it.resolveActivity(packageManager) != null) {
                startActivity(it)
            }else{
                println("No ${it.`package`}")
            }
        }
    }

    //Implicit intent just specify action, and android check which app can satisfy the intent and show chooser to the user
    private fun implicitIntent(){
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL,"text@co.co")
            putExtra(Intent.EXTRA_SUBJECT,"Meet up at 9")
            putExtra(Intent.EXTRA_TEXT,"Hi all, lets meet at 9 today.")
        }
        if(intent.resolveActivity(packageManager)!=null){
            startActivity(intent)
        }
    }

}