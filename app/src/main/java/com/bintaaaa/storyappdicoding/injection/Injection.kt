package com.bintaaaa.storyappdicoding.injection

import android.content.Context
import com.bintaaaa.storyappdicoding.data.dataSources.ApiConfig
import com.bintaaaa.storyappdicoding.data.dataSources.AuthenticationService
import com.bintaaaa.storyappdicoding.presentation.viewModel.AuthenticationViewModel
import com.bintaaaa.storyappdicoding.repository.StoryRepository

object Injection {
    fun provideRepository(context: Context): StoryRepository{
        val authenticationService = ApiConfig.authentication()
        return StoryRepository.getInstance(authenticationService)
    }
}