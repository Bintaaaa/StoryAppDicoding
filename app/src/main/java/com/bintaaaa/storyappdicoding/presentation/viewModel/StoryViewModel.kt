package com.bintaaaa.storyappdicoding.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bintaaaa.storyappdicoding.data.models.resposne.StoryItem
import com.bintaaaa.storyappdicoding.repository.StoryRepository
import java.io.File

class StoryViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun stories(): LiveData<PagingData<StoryItem>> = storyRepository.getStories().cachedIn(viewModelScope)
    fun storyDetail(storyId: String) = storyRepository.getDetailStory(storyId)
    fun post(imageUri: File, description: String) = storyRepository.post(imageUri, description)
}