package com.ananth.androidthings.testing.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.PrimaryKey
import com.ananth.androidthings.testing.data.local.ShoppingItem
import com.ananth.androidthings.testing.data.remote.responses.ImageResponse
import com.ananth.androidthings.testing.other.Event
import com.ananth.androidthings.testing.other.Resource
import com.ananth.androidthings.testing.repositories.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val repository: ShoppingRepository
) : ViewModel() {

    val shoppingItems = repository.observeAllShoppingItem()

    val totalPrice = repository.observeTotalPrice()

    private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images: LiveData<Event<Resource<ImageResponse>>> = _images

    private val _imageUrl = MutableLiveData<String>()
    val imagesUrl: LiveData<String> = _imageUrl


    private val _insertShoppingItemStatus = MutableLiveData<Resource<ShoppingItem>>()
    val insertShoppingItemStatus: LiveData<Resource<ShoppingItem>> = _insertShoppingItemStatus

    fun setImageUrl(url: String) = _imageUrl.postValue(url)

    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem)
    }

    fun insertShoppingItemIntoDB(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }

    fun insertShoppingItem(name: String, amount: String, priceString: String) {

    }

    fun searchForImage(imageQuery:String){

    }
}