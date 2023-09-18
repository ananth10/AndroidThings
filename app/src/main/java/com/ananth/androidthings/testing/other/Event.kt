package com.ananth.androidthings.testing.other

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    //returns the content and prevents its use again
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    //Returns the content, even if its already been handled
    fun peekContent(): T = content
}