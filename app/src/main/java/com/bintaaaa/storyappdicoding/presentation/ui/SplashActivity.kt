package com.bintaaaa.storyappdicoding.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bintaaaa.storyappdicoding.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler(Looper.getMainLooper()).postDelayed({
                forwarderActivity()
        },2000)

    }

    private fun forwarderActivity(){
        val sharedPref = getSharedPreferences(SignInActivity.MY_PREF_NAME, Context.MODE_PRIVATE)
        val token = sharedPref.getString(SignInActivity.TOKEN,"")
        val intent: Intent = if(token.isNullOrEmpty()){
            Intent(this@SplashActivity, SignInActivity::class.java)
        }else{
            Intent(this@SplashActivity, HomeActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}