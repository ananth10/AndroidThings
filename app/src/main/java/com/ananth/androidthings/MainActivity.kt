package com.ananth.androidthings

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ananth.androidthings.intent.MyIntentActivity
import com.ananth.androidthings.ui.theme.AndroidThingsTheme

class MainActivity : ComponentActivity() {
    val viewModel by viewModels<ImageViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidThingsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        viewModel.uri?.let {
                            AsyncImage(model = viewModel.uri, contentDescription = null)
                        }

                        Button(modifier = Modifier
                            .width(150.dp)
                            .height(70.dp), onClick = {
                            Intent(applicationContext, MyIntentActivity::class.java).also {
                                startActivity(it)
                            }
                        }) {
                            Text(text = "Click")
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        SpanText()
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
        } else {
            intent?.getParcelableExtra(Intent.EXTRA_STREAM)
        }
        uri?.let {
            viewModel.updateUri(uri)

        }

    }
}

@Composable
fun SpanText() {
    val tnc = "Terms and Condition"
    val privacyPolicy = "Privacy policy"
    val annotatedString = buildAnnotatedString {
        append("I have read ")
        withStyle(style = SpanStyle(color = Color.Red), ) {
            pushStringAnnotation(tag = tnc, annotation = tnc)
            append(tnc)
        }
        append(" and ")
        withStyle(style = SpanStyle(color = Color.Red), ) {
            pushStringAnnotation(tag = privacyPolicy, annotation = privacyPolicy)
            append(privacyPolicy)
        }
    }

    ClickableText(text = annotatedString, onClick = { offset ->
        annotatedString.getStringAnnotations(offset,offset).firstOrNull()?.let { span->
            println("Clicked on ${span.item}")
        }
    })
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidThingsTheme {
        Greeting("Android")
    }
}