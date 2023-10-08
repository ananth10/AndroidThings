package com.ananth.androidthings.handleApi

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type


class NetworkResultCallAdapter(
    private val resultType: Type
):CallAdapter<Type,Call<NetworkResult<Type>>>
{
    override fun responseType() = resultType

    override fun adapt(call: Call<Type>): Call<NetworkResult<Type>> {
        return NetworkResultCall(call)
    }
}