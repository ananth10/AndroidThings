package com.ananth.androidthings.testing.ui

import androidx.compose.ui.unit.Constraints
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.PrimaryKey
import com.ananth.androidthings.testing.data.local.ShoppingItem
import com.ananth.androidthings.testing.data.remote.responses.ImageResponse
import com.ananth.androidthings.testing.other.Constants
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


    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus: LiveData<Event<Resource<ShoppingItem>>> =
        _insertShoppingItemStatus

    fun setImageUrl(url: String) = _imageUrl.postValue(url)

    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem)
    }

    fun insertShoppingItemIntoDB(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }

    fun insertShoppingItem(name: String, amount: String, price: String) {
        if (name.isEmpty() || amount.isEmpty() || price.isEmpty()) {
            _insertShoppingItemStatus.postValue(
                Event(
                    Resource.error(
                        "The fields must not be empty",
                        null
                    )
                )
            )
            return
        }
        if (name.length > Constants.MAX_NAME_LENGTH) {
            _insertShoppingItemStatus.postValue(
                Event(
                    Resource.error(
                        "The name of the item must not exceed ${Constants.MAX_NAME_LENGTH} char",
                        null
                    )
                )
            )
            return
        }
        if (price.length > Constants.MAX_PRICE_LENGTH) {
            _insertShoppingItemStatus.postValue(
                Event(
                    Resource.error(
                        "The price of the item must not exceed ${Constants.MAX_PRICE_LENGTH} char",
                        null
                    )
                )
            )
            return
        }
        val amount = try {
            amount.toInt()
        } catch (e: Exception) {
            _insertShoppingItemStatus.postValue(
                Event(
                    Resource.error(
                        "Please enter the valid amount",
                        null
                    )
                )
            )
            return
        }

        val shoppingItem = ShoppingItem(name, amount, price.toFloat(), _imageUrl.value ?: "")
        insertShoppingItemIntoDB(shoppingItem)
        setImageUrl("")
        _insertShoppingItemStatus.postValue(Event(Resource.success(shoppingItem)))
    }

    fun searchForImage(imageQuery: String) {
    if(imageQuery.isEmpty())
        return

     _images.value = Event(Resource.loading(null))

     viewModelScope.launch {
         val response = repository.searchForImage(imageQuery)
         _images.value = Event(response)
     }
    }
}