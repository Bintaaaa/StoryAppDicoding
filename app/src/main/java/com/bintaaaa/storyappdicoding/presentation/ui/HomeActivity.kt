package com.bintaaaa.storyappdicoding.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bintaaaa.storyappdicoding.common.api.Result
import com.bintaaaa.storyappdicoding.databinding.ActivityHomeBinding
import com.bintaaaa.storyappdicoding.presentation.viewModel.StoryViewModel
import com.bintaaaa.storyappdicoding.presentation.viewModel.ViewModelFactory

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.rvStory
        recyclerView.setHasFixedSize(true)

        fetchStory()
    }

    private fun fetchStory(){
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: StoryViewModel by viewModels {
            factory
        }
        val vCircular = binding.progressCircular
        val vRvStory = binding.rvStory
       viewModel.stories().observe(this){ result ->
           when(result){
               is Result.Loading ->{
                   vCircular.visibility = View.VISIBLE
               }
               is Result.Success ->{
                   vCircular.visibility = View.GONE
                   val adapter = StoryAdapter()
                   val stories =result.data?.listStory
                   Log.i("HomeActivity", stories.toString())
                   adapter.submitList(stories)

                   vRvStory.layoutManager = LinearLayoutManager(this)
                   vRvStory.adapter = adapter
               }
               is Result.Error -> {
                   vRvStory.visibility = View.GONE
                   Toast.makeText(
                       this,
                       "Error: " + result.message,
                       Toast.LENGTH_SHORT
                   ).show()
               }
           }
       }

    }
}