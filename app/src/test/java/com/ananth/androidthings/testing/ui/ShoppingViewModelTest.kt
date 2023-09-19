package com.ananth.androidthings.testing.ui

import com.ananth.androidthings.testing.getOrAwaitTestValue
import com.ananth.androidthings.testing.other.Constants
import com.ananth.androidthings.testing.other.Status
import com.ananth.androidthings.testing.repositories.FakeShoppingRepository
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ananth.androidthings.testing.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ShoppingViewModelTest {

    @get:Rule
    var instanceTaskExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ShoppingViewModel

    @Before
    fun setUp() {
        viewModel = ShoppingViewModel(FakeShoppingRepository())
    }

    @Test
    fun `should return error when insert shopping item with empty field`() {
        //Arrange

        //Act
        viewModel.insertShoppingItem("name", "", "3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitTestValue()
        //Assert

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `should return error when insert shopping item with too long name`() {
        //Arrange
        val name = buildString {
            for (i in 1..Constants.MAX_NAME_LENGTH + 1) {
                append(1)
            }
        }
        //Act
        viewModel.insertShoppingItem(name, "5", "3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitTestValue()
        //Assert

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `should return error when insert shopping item with too long price`() {
        //Arrange
        val price = buildString {
            for (i in 1..Constants.MAX_PRICE_LENGTH + 1) {
                append(1)
            }
        }
        //Act
        viewModel.insertShoppingItem("name", "5", price)

        val value = viewModel.insertShoppingItemStatus.getOrAwaitTestValue()
        //Assert

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `should return error when insert shopping item with too high amount`() {
        //Arrange
        //Act
        viewModel.insertShoppingItem("name", "5555555555555555555555", "3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitTestValue()
        //Assert

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `should return success when insert shopping item with valid input`() {
        //Arrange
        //Act
        viewModel.insertShoppingItem("name", "5", "3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitTestValue()
        //Assert

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `should clear image url after inserted shopping item`() {
        //Arrange
        //Act
        viewModel.insertShoppingItem("name", "5", "3.0")

        //Assert
        val result = viewModel.imagesUrl.getOrAwaitTestValue()
        assertThat(result).isEqualTo("")
    }

    @Test
    fun `should set given image url`() {
        //Arrange
        val inputUrl = "image_url"
        //Act
        viewModel.setImageUrl(inputUrl)
        //Assert
        val result = viewModel.imagesUrl.getOrAwaitTestValue()
        assertThat(result).isEqualTo(inputUrl)
    }
}