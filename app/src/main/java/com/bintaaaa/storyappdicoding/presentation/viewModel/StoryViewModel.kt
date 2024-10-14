package com.bintaaaa.storyappdicoding.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.bintaaaa.storyappdicoding.repository.StoryRepository
import java.io.File

class StoryViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun stories() = storyRepository.getStories().cachedIn(viewModelScope)
    fun storyDetail(storyId: String) = storyRepository.getDetailStory(storyId)
    fun post(imageUri: File, description: String) = storyRepository.post(imageUri, description)
}