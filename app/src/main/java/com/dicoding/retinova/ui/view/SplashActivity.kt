package com.dicoding.retinova.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.retinova.R
import com.dicoding.retinova.databinding.ActivitySplashBinding
import com.dicoding.retinova.ui.welcome.OnboardingActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgLogo.setImageResource(R.mipmap.ic_launcher)

        val splashScreenDuration = 2300L
        window.decorView.postDelayed({
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
        }, splashScreenDuration)
    }
}
