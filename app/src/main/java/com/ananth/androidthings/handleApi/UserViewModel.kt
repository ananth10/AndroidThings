package com.ananth.androidthings.handleApi

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class UserViewModel : ViewModel() {

    val successFlow = MutableStateFlow<List<FollowersEntity>>(ArrayList())
    val errorFlow = MutableStateFlow<String>("");

    fun makeApiCall() {
//        val apiService: GithubApiService = retrofit.create(GithubApiService::class.java)
        val apiService: GithubApiService = retrofit1.create(GithubApiService::class.java)

        //normal way
//        viewModelScope.launch {
//            val response = FollowersRepository(apiService).invoke()
//            println(response)
//        }

        //using NetworkResult
        viewModelScope.launch {
            val response = FollowersRepository1(apiService).invoke()
            response.onSuccess { followers ->
                println(followers)
            }.onError { code, message ->
                println("Error: $code and $message")
            }.onException { exception ->
                println("Exception:$exception")
            }
        }
    }
}


//this is normal way of calling api service
class FollowersRepository(
    private val apiService: GithubApiService
) {
    suspend operator fun invoke(): List<FollowersEntity> = try {
        apiService.getGithubUserFollowers("ananth10")
    } catch (e: HttpException) {
        //error handling
        emptyList()
    } catch (e: Throwable) {
        emptyList()
    }
}

//Using Wrapper class NetWorkResult
class FollowersRepository1(
    private val apiService: GithubApiService
) {
    suspend operator fun invoke(): NetworkResult<List<FollowersEntity>> {
        return apiService.getGithubUserFollowers1("ananth10")
    }
}

suspend fun <T : Any> handleApi(
    execute: suspend () -> Response<T>
): NetworkResult<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            NetworkResult.Success(body)
        } else {
            NetworkResult.Error(code = response.code(), message = response.message())
        }
    } catch (e: HttpException) {
        NetworkResult.Error(code = e.code(), message = e.message())
    } catch (e: Throwable) {
        NetworkResult.Exception(e)
    }
}


interface GithubApiService {
    //normal way
    @GET("users/{username}/followers")
    suspend fun getGithubUserFollowers(@Path("username") userName: String): List<FollowersEntity>

    //using NetworkResult
    @GET("users/{username}/followers")
    suspend fun getGithubUserFollowers1(@Path("username") userName: String): NetworkResult<List<FollowersEntity>>
}

const val BASE_URL = "https://api.github.com/"

val retrofit: Retrofit =
    Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()


//with custom call adapter
val retrofit1: Retrofit =
    Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(NetworkResultCallAdapterFactory.create()).build()