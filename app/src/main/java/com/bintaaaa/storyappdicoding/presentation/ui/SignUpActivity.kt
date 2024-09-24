package com.bintaaaa.storyappdicoding.presentation.ui

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bintaaaa.storyappdicoding.common.api.Result
import com.bintaaaa.storyappdicoding.data.models.body.RegisterBody
import com.bintaaaa.storyappdicoding.databinding.ActivitySignUpBinding
import com.bintaaaa.storyappdicoding.presentation.viewModel.AuthenticationViewModel
import com.bintaaaa.storyappdicoding.presentation.viewModel.ViewModelFactory

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playAnimation()

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: AuthenticationViewModel by viewModels {
            factory
        }
        val name = binding.edRegisterName.text
        val email = binding.edRegisterEmail.text
        val password = binding.edRegisterPassword.text
        val btnSignUp =  binding.btnSignUp
        btnSignUp.isEnabled = false
        textListener()

        btnSignUp.setOnClickListener{
            viewModel.signUp(RegisterBody(email = "$email", password = "$password", name = "$name")).observe(this){ result ->
                when(result){
                    is Result.Loading -> {
                       btnSignUp.isEnabled = false
                    }
                    is Result.Success-> {
                        Toast.makeText(
                            this,
                            "Registrasi Berhasil",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    }
                    is Result.Error ->{
                        btnSignUp.isEnabled = true
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
                val isUsernameFilled = binding.edRegisterName.text.toString().isNotEmpty()
                val isEmailFilled = binding.edRegisterEmail.text.toString().isNotEmpty()
                val isPasswordFilled = binding.edRegisterPassword.text.toString().isNotEmpty()
                if(binding.edRegisterEmail.error == null){
                    binding.btnSignUp.isEnabled = isUsernameFilled && isPasswordFilled && isEmailFilled
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        binding.edRegisterName.addTextChangedListener(textWatcher)
        binding.edRegisterEmail.addTextChangedListener(textWatcher)
        binding.edRegisterPassword.addTextChangedListener(textWatcher)
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 600
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }
}