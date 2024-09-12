package com.bintaaaa.storyappdicoding.repository

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.bintaaaa.storyappdicoding.common.api.Result
import com.bintaaaa.storyappdicoding.data.dataSources.AuthenticationService
import com.bintaaaa.storyappdicoding.data.models.body.AuthenticationBody
import com.bintaaaa.storyappdicoding.data.models.resposne.ErrorResponse
import com.bintaaaa.storyappdicoding.data.models.resposne.LoginResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryRepository(
    private val authenticationService: AuthenticationService
) {
    private val signInResult = MediatorLiveData<Result<LoginResponse>>()

    fun signIn(signInBody: AuthenticationBody): LiveData<Result<LoginResponse>>{
        signInResult.value = Result.Loading
        Log.i("StoryRepository.signIn","email ${signInBody.email} password ${signInBody.password}")
        val client = authenticationService.signIn(signInBody)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful){
                    val loginResponse = response.body()!!
                    signInResult.value = Result.Success(loginResponse)
                }else{
                    val errorBody = response.errorBody()?.string()
                    val gson = Gson()
                    try {
                        val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                        signInResult.value = Result.Error(errorResponse.message)
                    } catch (e: Exception) {
                        signInResult.value = Result.Error("An unknown error occurred")
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.i("SignIn", "the message is ${t.message}")
                signInResult.value = Result.Error(t.message.toString())
            }
        })
        return signInResult
    }

    companion object{
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(authenticationService: AuthenticationService): StoryRepository = instance ?: synchronized(this){
            instance ?: StoryRepository(authenticationService)
        }.also { instance = it }
    }
}