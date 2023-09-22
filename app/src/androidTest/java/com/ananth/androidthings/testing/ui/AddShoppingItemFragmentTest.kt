package com.ananth.androidthings.testing.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.ananth.androidthings.testing.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import com.ananth.androidthings.R
import com.ananth.androidthings.testing.getOrAwaitValue
import com.ananth.androidthings.testing.repositories.FakeShoppingRepository
import com.google.common.truth.Truth

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class AddShoppingItemFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instanceTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var viewModel: ShoppingViewModel

    @Before
    fun setUp() {
        hiltRule.inject()

        viewModel = ShoppingViewModel(FakeShoppingRepository())
    }

    @Test
    fun shouldPopupStackWheBackPressed() {
        //Arrange
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
        //Act
        pressBack()

        //Assertion
        verify(navController).popBackStack() //we are just verifying that popBackStack() called in navController
    }

    @Test
    fun shouldNavigateToImagePickFragmentWhenClickImage() {
        //Arrange
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
        //Act
        onView(withId(R.id.imagePickFragment)).perform(click())
        //Assertion
        verify(navController).navigate(AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment())
    }

    @Test
    fun shouldClearImageWhenPressBack() {
        //Arrange
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
        //Act
        pressBack()
        //Assertion
        val result = viewModel.imagesUrl.getOrAwaitValue()
        Truth.assertThat(result).isEqualTo("")
    }
}