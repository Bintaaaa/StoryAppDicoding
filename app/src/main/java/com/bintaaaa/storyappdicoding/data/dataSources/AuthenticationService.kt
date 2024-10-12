package com.bintaaaa.storyappdicoding.data.dataSources

import com.bintaaaa.storyappdicoding.data.models.body.LoginBody
import com.bintaaaa.storyappdicoding.data.models.body.RegisterBody
import com.bintaaaa.storyappdicoding.data.models.resposne.LoginResponse
import com.bintaaaa.storyappdicoding.data.models.resposne.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {
    @POST("login")
    fun signIn(@Body body: LoginBody): Call<LoginResponse>

    @POST("register")
    fun signUp(@Body body: RegisterBody): Call<RegisterResponse>
}

