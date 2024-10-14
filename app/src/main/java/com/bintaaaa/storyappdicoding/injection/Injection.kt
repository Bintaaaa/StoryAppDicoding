package com.bintaaaa.storyappdicoding.injection

import android.content.Context
import com.bintaaaa.storyappdicoding.data.dataSources.ApiConfig
import com.bintaaaa.storyappdicoding.data.dataSources.StoryPagingSource
import com.bintaaaa.storyappdicoding.data.dataSources.StoryService
import com.bintaaaa.storyappdicoding.repository.StoryRepository

object Injection {
    fun provideRepository(context: Context): StoryRepository{
        val authenticationService = ApiConfig.authentication(context)
        val storyService: StoryService = ApiConfig.story(context)
        return StoryRepository.getInstance(authenticationService = authenticationService, storyService = storyService)
    }
}