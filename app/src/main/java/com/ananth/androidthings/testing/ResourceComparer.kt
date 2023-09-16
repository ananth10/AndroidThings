package com.ananth.androidthings.testing

import android.content.Context

class ResourceComparer {

    fun isResourceEqual(context:Context,resId:Int,string:String):Boolean{
        return context.getString(resId)==string
    }
}