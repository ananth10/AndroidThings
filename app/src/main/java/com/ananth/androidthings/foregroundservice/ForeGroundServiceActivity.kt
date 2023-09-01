package com.ananth.androidthings.foregroundservice

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat

class ForeGroundServiceActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101
            )
        }
        setContent {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Foreground service")
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    Intent(applicationContext, RunningService::class.java).also {
                        it.action = RunningService.Action.START.toString()
                        startService(it)
                    }
                }) {
                    Text(text = "Start Service")
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = {
                    Intent(applicationContext, RunningService::class.java).also {
                        it.action = RunningService.Action.STOP.toString()
                        startService(it)
                    }
                }) {
                    Text(text = "Stop Service")
                }
            }
        }
    }
}