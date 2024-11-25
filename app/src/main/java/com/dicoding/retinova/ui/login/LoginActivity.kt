package com.dicoding.retinova.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.retinova.R
import com.dicoding.retinova.data.UserRepository
import com.dicoding.retinova.databinding.ActivityLoginBinding
import com.dicoding.retinova.data.di.Injection
import com.dicoding.retinova.data.pref.UserModel
import com.dicoding.retinova.data.pref.UserPreference
import com.dicoding.retinova.ui.ViewModelFactory
import com.dicoding.retinova.ui.register.RegisterActivity
import com.dicoding.retinova.ui.view.HomeActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userRepository: UserRepository
    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory(Injection.provideRepository(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userRepository = UserRepository.getInstance(UserPreference.getInstance(this))

        binding.btnMasuk.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val token = getString(R.string.dummy_token)
                val userModel = UserModel(email, token, true)
                lifecycleScope.launch {
                    userRepository.saveSession(userModel)
                    Toast.makeText(
                        this@LoginActivity,
                        getString(R.string.login_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    finish()
                }
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.empty_email_password),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        loginViewModel.isLoggedIn.observe(this) { isLoggedIn ->
            if (isLoggedIn) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, getString(R.string.wrong_email_password), Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvRegisterNow.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
