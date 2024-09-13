package com.bintaaaa.storyappdicoding.data.dataSources

import com.bintaaaa.storyappdicoding.data.models.body.AuthenticationBody
import com.bintaaaa.storyappdicoding.data.models.resposne.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {
    @POST("login")
    fun signIn(@Body body: AuthenticationBody): Call<LoginResponse>
}

