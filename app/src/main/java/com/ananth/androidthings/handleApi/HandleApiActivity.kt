package com.ananth.androidthings.handleApi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ananth.androidthings.ui.theme.AndroidThingsTheme
import kotlinx.coroutines.launch

class HandleApiActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val userViewModel = viewModel<UserViewModel>()
            val scope = rememberCoroutineScope()
            LaunchedEffect(userViewModel.successFlow) {
                scope.launch {
                    userViewModel.makeApiCall()

                }
            }
            AndroidThingsTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                            Text("Api Call")
                    }
                }
            }
        }
    }
}