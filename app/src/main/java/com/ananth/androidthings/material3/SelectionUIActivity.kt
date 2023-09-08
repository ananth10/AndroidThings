package com.ananth.androidthings.material3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import com.ananth.androidthings.ui.theme.AndroidThingsTheme

class SelectionUIActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidThingsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        CheckBoxes()
                        Spacer(modifier = Modifier.height(24.dp))
                        MySwitch()
                        Spacer(modifier = Modifier.height(24.dp))
                        RadioButtons()
                    }
                }
            }
        }
    }

}

data class ToggleableInfo(
    val isChecked: Boolean,
    val text: String
)

@Composable
fun CheckBoxes() {
    val checkBoxes = remember {
        mutableStateListOf(
            ToggleableInfo(false, "Photos"),
            ToggleableInfo(false, "Videos"),
            ToggleableInfo(false, "Audios"),
        )
    }

    var triState by remember { mutableStateOf(ToggleableState.Indeterminate) }

    val toggleTriState = {
        triState = when (triState) {
            ToggleableState.Indeterminate -> ToggleableState.On
            ToggleableState.On -> ToggleableState.Off
            else -> ToggleableState.On
        }

        checkBoxes.indices.forEach { index ->
            checkBoxes[index] = checkBoxes[index].copy(isChecked = triState == ToggleableState.On)
        }
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { toggleTriState() }
            .padding(end = 16.dp)
    ) {
        TriStateCheckbox(state = triState, onClick = toggleTriState)
        Text(text = "File types")
    }

    checkBoxes.forEachIndexed { index, info ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 8.dp, end = 16.dp)
                .clickable {
                    checkBoxes[index] = info.copy(isChecked = !info.isChecked)
                }
        ) {
            Checkbox(checked = info.isChecked, onCheckedChange = { checked ->
                checkBoxes[index] = info.copy(isChecked = checked)
            })
            Text(text = info.text)
        }
    }
}

@Composable
fun MySwitch() {
    var switch by remember {
        mutableStateOf(ToggleableInfo(false, "Dark mode"))
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = switch.text)
        Spacer(modifier = Modifier.weight(1f))
        Switch(checked = switch.isChecked, onCheckedChange = { checked ->
            switch = switch.copy(isChecked = checked)
        },
            thumbContent = {
                ThumbnailIcon(switch.isChecked)
            }
        )
    }
}

@Composable
fun ThumbnailIcon(state: Boolean) {
    Icon(
        imageVector = if (state) Icons.Outlined.Check else Icons.Outlined.Close,
        contentDescription = null
    )
}

@Composable
fun RadioButtons() {
    val radioButtons = remember {
        mutableStateListOf(
            ToggleableInfo(true, "Photos"),
            ToggleableInfo(false, "Videos"),
            ToggleableInfo(false, "Audios"),
        )
    }

    radioButtons.forEachIndexed { index, info ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    radioButtons.replaceAll {
                        it.copy(isChecked = it.text == info.text)
                    }
                }
        ) {
            RadioButton(selected = info.isChecked, onClick = {
                radioButtons.replaceAll {
                    it.copy(isChecked = it.text == info.text)
                }
            })
            Text(text = info.text)
        }
    }
}