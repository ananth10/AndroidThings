package com.ananth.androidthings.testing

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class GeneralUtilTest{

    @Test
    fun `should return false when parenthesis count not matched`(){

        val result = GeneralUtil.checkParenthesis("This is add (4+3")

        assertThat(result).isFalse()

    }

    @Test
    fun `should return true when parenthesis count matched`(){

        val result = GeneralUtil.checkParenthesis("This is add (4+3)")

        assertThat(result).isTrue()

    }

    @Test
    fun `should return false when bracket count not matched`(){

        val result = GeneralUtil.checkParenthesis("This is add [4+3")

        assertThat(result).isFalse()

    }

    @Test
    fun `should return true when bracket count matched`(){

        val result = GeneralUtil.checkParenthesis("This is add [4+3]")

        assertThat(result).isTrue()

    }
}