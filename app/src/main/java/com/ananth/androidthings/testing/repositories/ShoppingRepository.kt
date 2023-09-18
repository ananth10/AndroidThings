package com.ananth.androidthings.testing.repositories

import androidx.lifecycle.LiveData
import com.ananth.androidthings.testing.data.local.ShoppingItem
import com.ananth.androidthings.testing.data.remote.responses.ImageResponse
import com.ananth.androidthings.testing.other.Resource
import retrofit2.Response

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItem():LiveData<List<ShoppingItem>>

    fun observeTotalPrice():LiveData<Float>

    suspend fun searchForImage(imageQuery:String):Resource<ImageResponse>
}