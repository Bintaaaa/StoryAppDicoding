package com.bintaaaa.storyappdicoding.presentation.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bintaaaa.storyappdicoding.common.api.Result
import com.bintaaaa.storyappdicoding.common.utils.reduceFileImage
import com.bintaaaa.storyappdicoding.common.utils.uriToFile
import com.bintaaaa.storyappdicoding.databinding.ActivityCreatePostBinding
import com.bintaaaa.storyappdicoding.presentation.viewModel.StoryViewModel
import com.bintaaaa.storyappdicoding.presentation.viewModel.ViewModelFactory

class CreatePostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreatePostBinding
    private var currentImageUri: Uri? = null
    private lateinit var ivGallery: ImageView
    private lateinit var openGallery: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)



        openGallery = binding.cvGallery
        ivGallery = binding.ivGallery
        ivGallery.visibility = View.GONE
        val description = binding.edAddDescription
        val button = binding.buttonAdd



        openGallery.setOnClickListener {
            launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }


        val factory = ViewModelFactory.getInstance(this)
        val viewModel: StoryViewModel by viewModels {
            factory
        }



        button.setOnClickListener {
            if(currentImageUri == null){
                Toast.makeText(
                    this,
                    "Error: " + "Pilih Gambar Terlebih Dahulu",
                    Toast.LENGTH_SHORT
                ).show()
            }

            if( description.text == null){
                Toast.makeText(
                    this,
                    "Error: " + "Isi Deskripsinya ya!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            if(description.text != null && currentImageUri != null){
                val image = uriToFile(currentImageUri!!, this).reduceFileImage()
                viewModel.post(imageUri = image, description= description.text.toString()).observe(this){result->
                    when(result){
                        is Result.Loading -> {
                            button.isEnabled = false
                        }
                        is Result.Success-> {
                            binding.buttonAdd.isEnabled = true
                            Toast.makeText(
                                this,
                                "Berhasil Membuat Story",
                                Toast.LENGTH_SHORT
                            ).show()
                            viewModel.stories()
                            val intent = Intent()
                            setResult(Activity.RESULT_OK, intent)
                            finish()
                        }
                        is Result.Error ->{
                            binding.buttonAdd.isEnabled = true
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

    }

    private fun showImage() {
        currentImageUri?.let {
            openGallery.visibility = View.GONE
            ivGallery.visibility = View.VISIBLE
            Log.d("Image URI", "showImage: $it")
            binding.ivGallery.setImageURI(it)
        }
    }

    private val launcherGallery = this.registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

}