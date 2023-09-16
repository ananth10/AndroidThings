package com.ananth.androidthings.testing


import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import com.ananth.androidthings.R
import org.junit.Before

class ResourceComparerTest {

    private lateinit var resourceComparer: ResourceComparer

    @Before
    fun setUp() {
        resourceComparer = ResourceComparer()
    }

    @Test
    fun shouldReturnTrueWhenResIdMatchedGivenString() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isResourceEqual(context, R.string.test_key, "Test Case")
        assertThat(result).isTrue()
    }

    @Test
    fun shouldReturnFalseWhenResIdNotMatchedGivenString() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isResourceEqual(context, R.string.test_key, "Test Case")
        assertThat(result).isTrue()
    }
}