package com.ananth.androidthings.broadcast

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import com.ananth.androidthings.ui.theme.AndroidThingsTheme

public class BroadCastActivity : ComponentActivity(){
     private val airPlaneModeReceiver = AirPlaneModeReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(airPlaneModeReceiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
        setContent {
            AndroidThingsTheme {
                Column {
                    Text(text = "Broadcast Activity")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(airPlaneModeReceiver)
    }
}