package com.bintaaaa.storyappdicoding.presentation.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bintaaaa.storyappdicoding.injection.Injection
import com.bintaaaa.storyappdicoding.repository.StoryRepository

class ViewModelFactory private constructor(private val storyRepository: StoryRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AuthenticationViewModel::class.java)){
            return  AuthenticationViewModel(storyRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

   companion object{
       @Volatile
       private var instance: ViewModelFactory? = null
       fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this){
           instance ?: ViewModelFactory(Injection.provideRepository(context))
       }.also { instance = it }
   }
}