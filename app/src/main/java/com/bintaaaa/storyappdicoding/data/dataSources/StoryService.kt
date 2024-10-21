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
import retrofit2.http.Query

interface StoryService {
    @GET("stories")
    suspend fun stories(@Query("size") size: Int, @Query("page") page: Int): StoriesResponse

    @GET("stories")
    suspend fun location(@Query("location") location: Int =1): StoriesResponse

    @GET("stories/{id}")
    fun detailStory(@Path("id")  id: String): Call<StoryDetailResponse>

    @Multipart
    @POST("stories")
    fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<UploadStoryResponse>
}