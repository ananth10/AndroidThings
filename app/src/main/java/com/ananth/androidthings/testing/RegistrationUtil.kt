package com.ananth.androidthings.testing

object RegistrationUtil {

    private val existingUsers = listOf("Peter", "Carl")

    /**
     * the input is not valid if
     * ...the username/password is empty
     * ...the username is already taken
     * ...the confirm password is not as same as password
     * ...the password is less than 2 digits
     * */
    fun validateRegistrationInput(
        userName: String,
        password: String,
        confirmPassword: String

    ): Boolean {
        if (userName.isEmpty() || password.isEmpty()) {
            return false
        }
        if (userName in existingUsers) {
            return false
        }
        if (password != confirmPassword) {
            return false
        }
        if (password.count { it.isDigit() } < 2) {
            return false
        }

        return true
    }

}