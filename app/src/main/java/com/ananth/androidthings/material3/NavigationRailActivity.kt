package com.ananth.androidthings.material3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import com.ananth.androidthings.ui.theme.AndroidThingsTheme


data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

class NavigationRailActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val list = listOf(
                NavigationItem("Home", Icons.Filled.Home, Icons.Outlined.Home, false),
                NavigationItem("Chat", Icons.Filled.Email, Icons.Outlined.Email, false, 45),
                NavigationItem(
                    "Settings",
                    Icons.Filled.Settings,
                    Icons.Outlined.Settings,
                    true
                )
            )
            AndroidThingsTheme {
                val windowSize = calculateWindowSizeClass(this)
                val showNavigationRail =
                    windowSize.widthSizeClass != WindowWidthSizeClass.Compact
                var selectedIndex by remember {
                    mutableIntStateOf(0)
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Scaffold(
                        bottomBar = {
                            if (!showNavigationRail) {
                                //Navigation bar
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    ) { values ->

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(values)
                                .padding(start = if (showNavigationRail) 80.dp else 0.dp)
                        ) {
                            items(100) {
                                Text(text = "Item $it", modifier = Modifier.padding(16.dp))
                            }
                        }
                    }

                }
                if (showNavigationRail) {
                    NavigationSideBar(
                        items = list,
                        selectedItemIndex = selectedIndex,
                        onNavigate = {
                            selectedIndex = it
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun NavigationSideBar(
    items: List<NavigationItem>,
    selectedItemIndex: Int,
    onNavigate: (Int) -> Unit
) {

    NavigationRail(
        header = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
            FloatingActionButton(
                onClick = { /*TODO*/ },
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface).offset(x=(-1).dp)
    ) {

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.Bottom)
        ) {
            items.forEachIndexed { index, item ->
                NavigationRailItem(
                    selected = selectedItemIndex == index,
                    onClick = {
                        onNavigate(index)
                    },
                    icon = {
                        NavigationIcon(item, selectedItemIndex == index)
                    },
                    label = { Text(text = item.title) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationIcon(
    item: NavigationItem,
    selected: Boolean
) {

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
            imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
            contentDescription = item.title
        )
    }
}