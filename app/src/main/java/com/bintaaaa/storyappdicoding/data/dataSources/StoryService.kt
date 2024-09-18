package com.bintaaaa.storyappdicoding.data.dataSources

import com.bintaaaa.storyappdicoding.data.models.body.StoryDetailResponse
import com.bintaaaa.storyappdicoding.data.models.resposne.LoginResponse
import com.bintaaaa.storyappdicoding.data.models.resposne.StoriesResponse
import com.bintaaaa.storyappdicoding.data.models.resposne.StoryItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface StoryService {
    @GET("stories")
    fun stories(): Call<StoriesResponse>

    @GET("stories/{id}")
    fun detailStory(@Path("id")  id: String): Call<StoryDetailResponse>
}