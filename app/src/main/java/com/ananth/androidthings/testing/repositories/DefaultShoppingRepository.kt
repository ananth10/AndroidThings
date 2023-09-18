package com.ananth.androidthings.testing.repositories

import androidx.lifecycle.LiveData
import com.ananth.androidthings.testing.data.local.ShoppingDao
import com.ananth.androidthings.testing.data.local.ShoppingItem
import com.ananth.androidthings.testing.data.remote.PixabayApi
import com.ananth.androidthings.testing.data.remote.responses.ImageResponse
import com.ananth.androidthings.testing.other.Resource
import com.ananth.androidthings.testing.other.Status
import retrofit2.Response
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayApi: PixabayApi
) : ShoppingRepository {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItem(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return try {
            val response = pixabayApi.searchForImage(imageQuery)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Unknown error occurred", null)
            } else {
                Resource.error("Unknown error occurred", null)
            }
        } catch (e: Exception) {
            Resource.error("Could not reach the server. Check your internet connection", null)
        }
    }
}