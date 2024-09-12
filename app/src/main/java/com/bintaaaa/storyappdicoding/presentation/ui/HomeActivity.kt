package com.bintaaaa.storyappdicoding.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bintaaaa.storyappdicoding.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}