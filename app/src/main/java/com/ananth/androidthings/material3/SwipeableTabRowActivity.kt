package com.ananth.androidthings.material3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.ananth.androidthings.ui.theme.AndroidThingsTheme


data class TabRow(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector
)

class SwipeableTabRowActivity : ComponentActivity() {


    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tabItem = listOf(
            TabRow(
                "Home",
                Icons.Filled.Home,
                Icons.Outlined.Home
            ),
            TabRow(
                "Browse",
                Icons.Filled.ShoppingCart,
                Icons.Outlined.ShoppingCart
            ),
            TabRow(
                "Account",
                Icons.Filled.AccountCircle,
                Icons.Outlined.AccountCircle
            )
        )
        setContent {
            AndroidThingsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var selectedTabIndex by remember {
                        mutableIntStateOf(0)
                    }
                    val pagerState = rememberPagerState(0)

                    LaunchedEffect(selectedTabIndex) {
                        pagerState.animateScrollToPage(selectedTabIndex)
                    }

                    LaunchedEffect(pagerState.currentPage) {
                        if(!pagerState.isScrollInProgress) {
                            selectedTabIndex = pagerState.settledPage
                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        TabRow(selectedTabIndex = selectedTabIndex) {
                            tabItem.forEachIndexed { index, tabRow ->
                                Tab(selected = index == selectedTabIndex, onClick = {
                                    selectedTabIndex = index
                                },
                                    text = {
                                        Text(text = tabRow.title)
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = if (index == selectedTabIndex) tabRow.selectedIcon else tabRow.unSelectedIcon,
                                            contentDescription = tabRow.title
                                        )
                                    }
                                )
                            }
                        }
                        HorizontalPager(
                            pageCount = tabItem.size, state = pagerState, modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) { index ->
                            Box(
                                Modifier.fillMaxSize(),
                                Alignment.Center
                            ) {
                                Text(text = tabItem[index].title)
                            }

                        }
                    }

                }
            }
        }
    }
}