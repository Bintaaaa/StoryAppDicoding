package com.bintaaaa.storyappdicoding.presentation.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bintaaaa.storyappdicoding.common.api.Result
import com.bintaaaa.storyappdicoding.common.`interface`.ClickListener
import com.bintaaaa.storyappdicoding.data.models.resposne.StoryItem
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

        binding.floatingActionButton.setOnClickListener{
            val intent = Intent(this@HomeActivity, CreatePostActivity::class.java)
            startActivity(intent)
        }

        binding.actionLogout.setOnClickListener {
            val preferences: SharedPreferences = getSharedPreferences(SignInActivity.MY_PREF_NAME, 0)
            preferences.edit().remove(SignInActivity.TOKEN).apply()
            val intent =  Intent(this@HomeActivity, SignInActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
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

                   adapter.onSetItemClick(object : ClickListener<StoryItem>{
                       override fun onItemClick(item: StoryItem) {
                           val detailIntent = Intent(this@HomeActivity, StoryDetailActivity::class.java)
                           detailIntent.putExtra(StoryDetailActivity.EXTRA_STORY_ID, item.id)
                           startActivity(detailIntent,
                               ActivityOptionsCompat.makeSceneTransitionAnimation(this@HomeActivity).toBundle())
                       }

                   })
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