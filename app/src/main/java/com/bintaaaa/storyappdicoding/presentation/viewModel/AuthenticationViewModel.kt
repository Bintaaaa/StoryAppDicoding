package com.bintaaaa.storyappdicoding.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.bintaaaa.storyappdicoding.data.models.body.LoginBody
import com.bintaaaa.storyappdicoding.data.models.body.RegisterBody
import com.bintaaaa.storyappdicoding.repository.StoryRepository

class AuthenticationViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun signIn(params: LoginBody) = storyRepository.signIn(params)
    fun signUp(params: RegisterBody) = storyRepository.signUp(params)
}