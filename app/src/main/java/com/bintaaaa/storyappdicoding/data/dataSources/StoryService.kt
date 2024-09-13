package com.bintaaaa.storyappdicoding.data.dataSources

import com.bintaaaa.storyappdicoding.data.models.resposne.LoginResponse
import com.bintaaaa.storyappdicoding.data.models.resposne.StoriesResponse
import retrofit2.Call
import retrofit2.http.GET

interface StoryService {
    @GET("stories")
    fun stories(): Call<StoriesResponse>
}