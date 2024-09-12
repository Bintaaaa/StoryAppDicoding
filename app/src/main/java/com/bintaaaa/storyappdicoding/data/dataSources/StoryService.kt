package com.bintaaaa.storyappdicoding.data.dataSources

import com.bintaaaa.storyappdicoding.data.models.resposne.LoginResponse
import retrofit2.Call
import retrofit2.http.GET

interface StoryService {
    @GET("story")
    fun stories(): Call<LoginResponse>
}