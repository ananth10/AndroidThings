package com.ananth.androidthings.material3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ananth.androidthings.R
import com.ananth.androidthings.ui.theme.AndroidThingsTheme
import kotlinx.coroutines.launch

class BottomSheetActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidThingsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val sheetState = rememberModalBottomSheetState()
                    var isSheetOpen by rememberSaveable {
                        mutableStateOf(false)
                    }
                    val scaffoldState = rememberBottomSheetScaffoldState()
                    val scope = rememberCoroutineScope()
//                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                        Button(onClick = {
//                            isSheetOpen = true
//                        }) {
//                            Text(text = "Open sheet")
//                        }
//                    }

                    BottomSheetScaffold(
                        scaffoldState = scaffoldState,
                        sheetContent = {
                            Image(
                                painter = painterResource(id = R.drawable.krimit),
                                contentDescription = null,
                                Modifier.fillMaxSize()
                            )
                        },
                        sheetPeekHeight = 20.dp
                    ) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Button(onClick = {
                                scope.launch {
                                    scaffoldState.bottomSheetState.expand()
                                }
                            }) {
                                Text(text = "Open sheet")
                            }
                        }
                    }

//                    if (isSheetOpen) {
//                        ModalBottomSheet(
//                            sheetState = sheetState,
//                            onDismissRequest = {
//                                isSheetOpen = false
//                            }) {
//                            Image(
//                                painter = painterResource(id = R.drawable.krimit),
//                                contentDescription = null,
//                                Modifier.fillMaxSize()
//                            )
//
//                        }
//                    }

                }
            }
        }
    }
}