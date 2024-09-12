package com.bintaaaa.storyappdicoding.presentation.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bintaaaa.storyappdicoding.common.api.Result
import com.bintaaaa.storyappdicoding.data.models.body.AuthenticationBody
import com.bintaaaa.storyappdicoding.databinding.ActivitySignInBinding
import com.bintaaaa.storyappdicoding.presentation.viewModel.AuthenticationViewModel
import com.bintaaaa.storyappdicoding.presentation.viewModel.ViewModelFactory

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: AuthenticationViewModel by viewModels {
            factory
        }
        val email = binding.edLoginEmail.text
        val password = binding.edLoginPassword.text
        binding.btnSignIn.isEnabled = false
        textListener()

        binding.btnSignIn.setOnClickListener{
            viewModel.signIn(AuthenticationBody(email = "$email", password = "$password")).observe(this){ result ->
                when(result){
                    is Result.Loading -> {
                        binding.btnSignIn.isEnabled = false
                    }
                    is Result.Success-> {
                         val pref: SharedPreferences = this@SignInActivity.getSharedPreferences(MY_PREF_NAME, Context.MODE_PRIVATE)
                        pref.edit().putString(TOKEN,result.data.loginResult?.token.toString()).apply()
                        val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        startActivity(intent)
                    }
                    is Result.Error ->{
                        binding.btnSignIn.isEnabled = true
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

   private fun textListener(){
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val isUsernameFilled = binding.edLoginEmail.text.toString().isNotEmpty()
                val isPasswordFilled = binding.edLoginPassword.text.toString().isNotEmpty()

                binding.btnSignIn.isEnabled = isUsernameFilled && isPasswordFilled
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        binding.edLoginEmail.addTextChangedListener(textWatcher)
        binding.edLoginPassword.addTextChangedListener(textWatcher)
    }

    companion object{
        const val TOKEN = "TOKEN"
        const val MY_PREF_NAME = "MY_PREF_NAME"
    }
}


