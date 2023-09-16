package com.ananth.androidthings.testing

import org.junit.Test
import com.google.common.truth.Truth.assertThat



class RegistrationUtilTest {

    @Test
    fun `should return false when username empty`() {
        val result = RegistrationUtil.validateRegistrationInput(
            userName = "",
            password = "123",
            confirmPassword = "123"
        )

        assertThat(result).isFalse()
    }

    @Test
    fun `should return true when username not empty`() {
        val result = RegistrationUtil.validateRegistrationInput(
            userName = "John",
            password = "123",
            confirmPassword = "123"
        )

        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when username already taken`() {
        val result = RegistrationUtil.validateRegistrationInput(
            userName = "Peter",
            password = "123",
            confirmPassword = "123"
        )

        assertThat(result).isFalse()
    }

    @Test
    fun `should return false when confirm password not matched the password`() {
        val result = RegistrationUtil.validateRegistrationInput(
            userName = "John",
            password = "123",
            confirmPassword = "1234"
        )

        assertThat(result).isFalse()
    }

    @Test
    fun `should return false when password empty`() {
        val result = RegistrationUtil.validateRegistrationInput(
            userName = "John",
            password = "",
            confirmPassword = "1234"
        )

        assertThat(result).isFalse()
    }

    @Test
    fun `should return false when password less than 2 digits`() {
        val result = RegistrationUtil.validateRegistrationInput(
            userName = "John",
            password = "abc1",
            confirmPassword = "abc1"
        )

        assertThat(result).isFalse()
    }

    @Test
    fun `should return true when confirm password matched the password`() {
        val result = RegistrationUtil.validateRegistrationInput(
            userName = "John",
            password = "123",
            confirmPassword = "123"
        )

        assertThat(result).isTrue()
    }
}