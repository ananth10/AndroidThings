package com.ananth.androidthings.material3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.ananth.androidthings.ui.theme.AndroidThingsTheme


data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

class BottomNavigation : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val list = listOf(
            BottomNavigationItem("Home", Icons.Filled.Home, Icons.Outlined.Home, false),
            BottomNavigationItem("Chat", Icons.Filled.Email, Icons.Outlined.Email, false, 45),
            BottomNavigationItem("Settings", Icons.Filled.Settings, Icons.Outlined.Settings, true)
        )
        setContent {
            AndroidThingsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var selectedItem by rememberSaveable {
                        mutableIntStateOf(0)
                    }
                    Scaffold(
                        bottomBar = {
                            NavigationBar {
                                list.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        selected = selectedItem == index,
                                        onClick = {
                                            selectedItem = index
//                                            navController.navigate(item.title)
                                        },
                                        label = {
                                            Text(text = item.title)
                                        },
                                        alwaysShowLabel = false,
                                        icon = {
                                            BadgedBox(badge = {

                                                if (item.badgeCount != null) {
                                                    Badge {
                                                        Text(text = item.badgeCount.toString())
                                                    }
                                                } else if (item.hasNews) {
                                                    Badge()
                                                }
                                            }) {
                                                Icon(
                                                    if (selectedItem == index) item.selectedIcon else item.unselectedIcon,
                                                    contentDescription = item.title
                                                )
                                            }
                                        })
                                }
                            }
                        }
                    ) { values ->

                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {

                        }
                    }
                }
            }
        }
    }
}