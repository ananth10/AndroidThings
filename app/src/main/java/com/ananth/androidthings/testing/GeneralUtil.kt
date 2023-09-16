package com.ananth.androidthings.testing

object GeneralUtil {

    /**
     * the parenthesis is not valid
     *--- Open/Closed parenthesis "(" ")" count not matched
     *--- Open/Closed brackets "[" "]" count not matched
     * */
    fun checkParenthesis(value:String):Boolean{
        if(value.count { it=='('} != value.count { it==')'}){
            return false
        }
        if(value.count { it=='['} != value.count { it==']'}){
            return false
        }
        return true
    }
}