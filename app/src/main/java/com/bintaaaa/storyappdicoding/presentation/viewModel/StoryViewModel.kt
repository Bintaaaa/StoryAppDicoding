package com.bintaaaa.storyappdicoding.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.bintaaaa.storyappdicoding.repository.StoryRepository
import java.io.File

class StoryViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun stories() = storyRepository.stories()
    fun storyDetail(storyId: String) = storyRepository.getDetailStory(storyId)
    fun post(imageUri: File, description: String) = storyRepository.post(imageUri, description)
}