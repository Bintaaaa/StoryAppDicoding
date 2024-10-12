package com.bintaaaa.storyappdicoding.data.dataSources

import com.bintaaaa.storyappdicoding.data.models.body.StoryDetailResponse
import com.bintaaaa.storyappdicoding.data.models.body.UploadStoryResponse
import com.bintaaaa.storyappdicoding.data.models.resposne.StoriesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface StoryService {
    @GET("stories?location=1")
    fun stories(): Call<StoriesResponse>

    @GET("stories/{id}")
    fun detailStory(@Path("id")  id: String): Call<StoryDetailResponse>

    @Multipart
    @POST("stories")
    fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<UploadStoryResponse>
}