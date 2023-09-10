package com.ananth.androidthings.material3

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import com.ananth.androidthings.ui.theme.AndroidThingsTheme

class BottomAppBarActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidThingsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            BottomAppBar(actions = {
                                IconButton(onClick = { }) {
                                    Icon(
                                        imageVector = Icons.Default.Favorite,
                                        contentDescription = "Favourite"
                                    )
                                }
                                IconButton(onClick = { }) {
                                    Icon(
                                        imageVector = Icons.Default.Call,
                                        contentDescription = "Call"
                                    )
                                }
                            },
                                floatingActionButton = {
                                    FloatingActionButton(onClick = { /*TODO*/ }) {
                                        Icon(Icons.Default.Phone, contentDescription = "phone")
                                    }
                                }
                            )
                        }
                    ) { values ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(values)
                        ) {

                        }
                    }
                }
            }
        }
    }
}