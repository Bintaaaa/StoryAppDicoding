package com.bintaaaa.storyappdicoding.data.dataSources

import android.content.Context
import com.bintaaaa.storyappdicoding.presentation.ui.SignInActivity
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    class AuthInterceptor(private val sharedPreferences: Context) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            // Retrieve token from SharedPreferences
            val token = sharedPreferences.getSharedPreferences(SignInActivity.MY_PREF_NAME, Context.MODE_PRIVATE)
                .getString(SignInActivity.TOKEN, "") // Replace "TOKEN_KEY" with your actual key

            // Modify the request to add the token to the headers

            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token") // Add token to header
                .build()

            return chain.proceed(newRequest)
        }
    }

    private fun retrofit(context: Context): Retrofit{
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).addInterceptor(AuthInterceptor(context)).build()

        val retrofit = Retrofit.Builder().baseUrl("https://story-api.dicoding.dev/v1/").addConverterFactory(GsonConverterFactory.create()).client(client).build()

        return retrofit
    }

    fun authentication(context: Context): AuthenticationService{
        return retrofit(context).create(AuthenticationService::class.java)
    }

    fun story(context: Context): StoryService{
        return retrofit(context).create(StoryService::class.java)
    }
}