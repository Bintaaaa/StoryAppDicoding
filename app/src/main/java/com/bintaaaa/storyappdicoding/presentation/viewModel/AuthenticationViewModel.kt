package com.bintaaaa.storyappdicoding.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.bintaaaa.storyappdicoding.data.models.body.AuthenticationBody
import com.bintaaaa.storyappdicoding.repository.StoryRepository

class AuthenticationViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun signIn(params: AuthenticationBody) = storyRepository.signIn(params)
}