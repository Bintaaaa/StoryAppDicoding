package com.bintaaaa.storyappdicoding.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bintaaaa.storyappdicoding.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}