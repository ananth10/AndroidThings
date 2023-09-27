package com.ananth.androidthings.lazycolumnwithcategories

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ananth.androidthings.ui.theme.AndroidThingsTheme

@OptIn(ExperimentalFoundationApi::class)
class LazyColumnWithCategoryActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val names = names.map {
            Category(it.key.toString(), it.value)
        }
        setContent {
            AndroidThingsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CategorizedLazyColumn(names)
                    }
                }
            }
        }
    }

    data class Category(
        val name: String,
        val item: List<String>
    )

    @Composable
    fun CategoryHeader(
        text: String,
        modifier: Modifier = Modifier
    ) {

        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(16.dp)
        )
    }

    @Composable
    fun CategoryItem(
        text: String,
        modifier: Modifier = Modifier
    ) {

        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        )
    }

    @Composable
    fun CategorizedLazyColumn(
        categories: List<Category>,
        modifier: Modifier = Modifier
    ) {

        LazyColumn(modifier = modifier) {
            categories.forEach { category ->
                stickyHeader {
                    CategoryHeader(text = category.name)
                }
                items(category.item) { text ->
                    CategoryItem(text = text)
                }
            }
        }
    }
}