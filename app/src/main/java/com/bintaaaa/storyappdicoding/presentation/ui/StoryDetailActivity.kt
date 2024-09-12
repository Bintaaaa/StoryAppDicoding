package com.bintaaaa.storyappdicoding.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bintaaaa.storyappdicoding.databinding.ActivityStoryDetailBinding

class StoryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoryDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}