package com.ananth.androidthings.crypto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ananth.androidthings.ui.theme.AndroidThingsTheme
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class CryptoActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cryptoManager = CryptoManager()
        setContent {
            AndroidThingsTheme {
                var messageToEncrypt by remember { mutableStateOf("") }
                var messageToDecrypt by remember {
                    mutableStateOf("")
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        value = messageToEncrypt,
                        placeholder = { Text(text = "Encrypt message") },
                        onValueChange = {
                            messageToEncrypt = it
                        },
                        shape = CircleShape,

                        )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                val bytes = messageToEncrypt.encodeToByteArray()
                                val file = File(filesDir, "secret.txt")
                                if (!file.exists()) {
                                    file.createNewFile()
                                }

                                val fos = FileOutputStream(file)
                                messageToDecrypt =
                                    cryptoManager.encrypt(bytes = bytes, outputStream = fos)
                                        .decodeToString()
                            }) {
                            Text(text = "Encrypt")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                val file = File(filesDir, "secret.txt")
                                messageToEncrypt =
                                    cryptoManager.decrypt(FileInputStream(file)).decodeToString()
                            }) {
                            Text(text = "Decrypt")
                        }
                    }

                    Text(modifier = Modifier.padding(10.dp), text = messageToDecrypt)
                }
            }
        }
    }
}

