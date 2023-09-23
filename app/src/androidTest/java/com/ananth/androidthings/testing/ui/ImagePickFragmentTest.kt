package com.ananth.androidthings.testing.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
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
import javax.inject.Inject
import com.ananth.androidthings.R
import com.ananth.androidthings.testing.adapters.ImageAdapter
import com.ananth.androidthings.testing.getOrAwaitValue
import com.ananth.androidthings.testing.repositories.FakeShoppingRepositoryAndroidTest
import org.mockito.Mockito.verify
import com.google.common.truth.Truth.assertThat

@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
class ImagePickFragmentTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory

    @Before
    fun setUp(){
        hiltRule.inject()
    }

    @Test
    fun shouldPopBackStackAndSetImageUrlWhenClickImage(){
        //Arrange
      val navController = mock(NavController::class.java)
      val imageUrl = "Test"
      val  testViewModel = ShoppingViewModel(FakeShoppingRepositoryAndroidTest())
       launchFragmentInHiltContainer<ImagePickFragment>(fragmentFactory = fragmentFactory){
           Navigation.setViewNavController(requireView(),navController)
           imageAdapter.images = listOf(imageUrl)
           viewModel = testViewModel
       }
        //Action
        onView(withId(R.id.rvImages)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageAdapter.ImageViewHolder>(0,click())
        )
        //Assert
        verify(navController).popBackStack()
        assertThat(testViewModel.imagesUrl.getOrAwaitValue()).isEqualTo(imageUrl)

    }
}