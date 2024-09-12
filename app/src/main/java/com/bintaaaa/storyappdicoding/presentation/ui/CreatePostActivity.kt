package com.bintaaaa.storyappdicoding.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bintaaaa.storyappdicoding.databinding.ActivityCreatePostBinding

class CreatePostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreatePostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}