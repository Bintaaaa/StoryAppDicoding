package com.bintaaaa.storyappdicoding.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.bintaaaa.storyappdicoding.common.api.Result
import com.bintaaaa.storyappdicoding.data.dataSources.AuthenticationService
import com.bintaaaa.storyappdicoding.data.dataSources.StoryService
import com.bintaaaa.storyappdicoding.data.models.body.LoginBody
import com.bintaaaa.storyappdicoding.data.models.body.RegisterBody
import com.bintaaaa.storyappdicoding.data.models.body.StoryDetailResponse
import com.bintaaaa.storyappdicoding.data.models.body.UploadStoryResponse
import com.bintaaaa.storyappdicoding.data.models.resposne.ErrorResponse
import com.bintaaaa.storyappdicoding.data.models.resposne.LoginResponse
import com.bintaaaa.storyappdicoding.data.models.resposne.RegisterResponse
import com.bintaaaa.storyappdicoding.data.models.resposne.StoriesResponse
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class StoryRepository(
    private val authenticationService: AuthenticationService,
    private val storyService: StoryService,
) {
    private val signInResult = MediatorLiveData<Result<LoginResponse>>()
    private val signUpResult = MediatorLiveData<Result<RegisterResponse>>()
    private val storiesResult = MediatorLiveData<Result<StoriesResponse>>()
    private val storyDetailResult = MediatorLiveData<Result<StoryDetailResponse>>()
    private val storyUploadResult = MediatorLiveData<Result<UploadStoryResponse>>()


    fun signIn(signInBody: LoginBody): LiveData<Result<LoginResponse>>{
        signInResult.value = Result.Loading
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

    fun signUp(signUpBody: RegisterBody): LiveData<Result<RegisterResponse>>{
        signUpResult.value = Result.Loading
        val client = authenticationService.signUp(signUpBody)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if(response.isSuccessful){
                    val body = response.body()
                    signUpResult.value = Result.Success(body)
                }else{
                    val errorBody = response.errorBody()?.string()
                    val gson = Gson()
                    try {
                        val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                        signUpResult.value = Result.Error(errorResponse.message)
                    } catch (e: Exception) {
                        signUpResult.value = Result.Error("An unknown error occurred")
                    }
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                signUpResult.value = Result.Error(t.message.toString())

                Log.i("signup", t.message.toString())
            }
        })
        return signUpResult
    }

    fun stories(): LiveData<Result<StoriesResponse>>{
        storiesResult.value = Result.Loading
        val client = storyService.stories()
        client.enqueue(object : Callback<StoriesResponse>{
            override fun onResponse(call: Call<StoriesResponse>, response: Response<StoriesResponse>) {
                if(response.isSuccessful){
                    val body = response.body()
                    storiesResult.value = Result.Success(body)
                }else{
                    val errorBody = response.errorBody()?.string()
                    val gson = Gson()
                    try {
                        val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                        storiesResult.value = Result.Error(errorResponse.message)
                    } catch (e: Exception) {
                        storiesResult.value = Result.Error("An unknown error occurred")
                    }
                }
            }

            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                Log.i("Stories", "the message is ${t.message}")
                storiesResult.value = Result.Error(t.message.toString())
            }
        })
        return  storiesResult
    }

    fun getDetailStory(storyId: String): LiveData<Result<StoryDetailResponse>>{
        storyDetailResult.value = Result.Loading
        val client = storyService.detailStory(storyId)
        client.enqueue(object : Callback<StoryDetailResponse>{
            override fun onResponse(call: Call<StoryDetailResponse>, response: Response<StoryDetailResponse>) {
                if(response.isSuccessful){
                    val body = response.body()
                    storyDetailResult.value = Result.Success(body)
                }else{
                    val errorBody = response.errorBody()?.string()
                    val gson = Gson()
                    try {
                        val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                        storyDetailResult.value = Result.Error(errorResponse.message)
                    } catch (e: Exception) {
                        storyDetailResult.value = Result.Error("An unknown error occurred")
                    }
                }
            }

            override fun onFailure(call: Call<StoryDetailResponse>, t: Throwable) {
                Log.i("Stories", "the message is ${t.message}")
                storyDetailResult.value = Result.Error(t.message.toString())
            }
        })
        return  storyDetailResult
    }

    fun post(imageUri: File, description: String): LiveData<Result<UploadStoryResponse>>{
        storyUploadResult.value = Result.Loading
        val desc = description.toRequestBody("text/plain".toMediaType())
        val imgReq = imageUri.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            imageUri.name,
            imgReq
        )
        val client = storyService.uploadImage(file = multipartBody,  description = desc)

        client.enqueue(object : Callback<UploadStoryResponse> {
            override fun onResponse(call: Call<UploadStoryResponse>, response: Response<UploadStoryResponse>) {
                if(response.isSuccessful){
                    val body = response.body()
                    storyUploadResult.value = Result.Success(body)
                }else{
                    val errorBody = response.errorBody()?.string()
                    val gson = Gson()
                    try {
                        val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                        storyUploadResult.value = Result.Error(errorResponse.message)
                    } catch (e: Exception) {
                        storyUploadResult.value = Result.Error("An unknown error occurred")
                    }
                }
            }

            override fun onFailure(call: Call<UploadStoryResponse>, t: Throwable) {
                storyUploadResult.value = Result.Error(t.message.toString())

                Log.i("signup", t.message.toString())
            }
        })
        return storyUploadResult
    }

    companion object{
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(authenticationService: AuthenticationService, storyService: StoryService): StoryRepository = instance ?: synchronized(this){
            instance ?: StoryRepository(authenticationService, storyService)
        }.also { instance = it }
    }
}