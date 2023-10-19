package com.ananth.androidthings.gestures

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import com.ananth.androidthings.R

@Composable
fun FullScreenPhoto(
    photo: Photo,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Scrim(onClose = onDismiss)
        PhotoImage(photo = photo)
    }
}

@Composable
fun Scrim(onClose: () -> Unit, modifier: Modifier = Modifier) {
    val strClose = stringResource(id = R.string.close)
    Box(modifier = modifier
        .fillMaxSize()
        .pointerInput(onClose) { detectTapGestures { onClose() } }
        .semantics { onClick(strClose) { onClose();true } }
        .focusable()
        .onKeyEvent {
            if (it.key == Key.Escape) {
                onClose();true
            } else {
                false
            }
        }
        .background(Color.DarkGray.copy(alpha = 0.75f))
    )

}