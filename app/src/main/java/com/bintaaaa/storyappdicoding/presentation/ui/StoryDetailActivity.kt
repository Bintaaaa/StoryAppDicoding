package com.bintaaaa.storyappdicoding.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.Visibility
import com.bintaaaa.storyappdicoding.common.api.Result
import com.bintaaaa.storyappdicoding.databinding.ActivityStoryDetailBinding
import com.bintaaaa.storyappdicoding.presentation.viewModel.StoryViewModel
import com.bintaaaa.storyappdicoding.presentation.viewModel.ViewModelFactory
import com.bumptech.glide.Glide

class StoryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoryDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val storyId = intent.getStringExtra(EXTRA_STORY_ID)
        getStoryDetail(storyId!!)
    }

    private fun getStoryDetail(storyId: String){
        val factory = ViewModelFactory.getInstance(this)
        val viewModel by viewModels<StoryViewModel>{
            factory
        }
        viewModel.storyDetail(storyId).observe(this) { result ->
            val image = binding.ivDetailPhoto
            val title = binding.tvDetailName
            val description = binding.tvDetailDescription
            val progressbar = binding.progressBar2
            when(result){
                is Result.Loading ->{
                    title.visibility = View.GONE
                    image.visibility = View.GONE
                    description.visibility = View.GONE
                    progressbar.visibility = View.VISIBLE
                }
                is Result.Success->{
                    val detail = result.data?.story
                    Log.i("StoryDetail", detail?.id.toString())

                    title.visibility = View.VISIBLE
                    title.text = detail?.name

                    image.visibility = View.VISIBLE
                    Glide.with(this@StoryDetailActivity).load(detail?.photoUrl).into(image)

                    description.visibility = View.VISIBLE
                    description.text =  detail?.description

                    progressbar.visibility = View.GONE
                }
                is Result.Error ->{
                    title.text = "Terjadi Kesalahan"
                    title.gravity = Gravity.CENTER
                    image.visibility = View.GONE
                    description.visibility = View.GONE
                    progressbar.visibility = View.GONE
                    Toast.makeText(
                        this,
                        "Error: " + result.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }


    companion object{
        const val EXTRA_STORY_ID = "EXTRA_STORY_ID"
    }
}