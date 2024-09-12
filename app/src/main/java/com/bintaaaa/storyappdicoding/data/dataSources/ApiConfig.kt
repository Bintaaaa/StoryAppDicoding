package com.bintaaaa.storyappdicoding.data.dataSources

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private fun retrofit(): Retrofit{
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder().baseUrl("https://story-api.dicoding.dev/v1/").addConverterFactory(GsonConverterFactory.create()).client(client).build()

        return retrofit
    }

    fun authentication(): AuthenticationService{
        return retrofit().create(AuthenticationService::class.java)
    }

    fun story(): StoryService{
        return retrofit().create(StoryService::class.java)
    }
}