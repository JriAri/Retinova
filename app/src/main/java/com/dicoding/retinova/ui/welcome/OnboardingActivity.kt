package com.dicoding.retinova.ui.welcome

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.retinova.ui.login.LoginActivity
import com.dicoding.retinova.R
import com.dicoding.retinova.data.UserRepository
import com.dicoding.retinova.data.pref.UserPreference
import com.dicoding.retinova.databinding.ActivityOnboardingBinding
import com.dicoding.retinova.ui.view.HomeActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userRepository = UserRepository.getInstance(UserPreference.getInstance(this))

        lifecycleScope.launch {
            val userSession = userRepository.getSession().first()
            if (userSession.isLogin) {
                startActivity(Intent(this@OnboardingActivity, HomeActivity::class.java))
                finish()
            }
        }

        val onboardingItems = listOf(
            OnboardingItem(
                R.drawable.deteksi_diabetes,
                getString(R.string.onboarding_title_detection),
                getString(R.string.onboarding_desc_detection)
            ),
            OnboardingItem(
                R.drawable.chatbot,
                getString(R.string.onboarding_title_chatbot),
                getString(R.string.onboarding_desc_chatbot)
            ),
            OnboardingItem(
                R.drawable.artikel,
                getString(R.string.onboarding_title_article),
                getString(R.string.onboarding_desc_article)
            )
        )

        val adapter = OnboardingAdapter(onboardingItems)
        binding.viewPager.adapter = adapter
        binding.dotsIndicator.attachTo(binding.viewPager)

        updateBottomSheetContent(onboardingItems[0])

        binding.btnNext.setOnClickListener {
            if (binding.viewPager.currentItem + 1 < onboardingItems.size) {
                binding.viewPager.currentItem += 1
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        binding.btnSkip.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.btnBack.setOnClickListener {
            if (binding.viewPager.currentItem > 0) {
                binding.viewPager.setCurrentItem(binding.viewPager.currentItem - 1, true)
            } else {
                finish()
            }
        }

        binding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateBottomSheetContent(onboardingItems[position])
                binding.btnNext.text = if (position == onboardingItems.size - 1)
                    getString(R.string.get_started) else getString(R.string.next)
                binding.btnBack.visibility = if (position > 0) View.VISIBLE else View.GONE
            }
        })
    }

    private fun updateBottomSheetContent(item: OnboardingItem) {
        binding.textTitle.text = item.title
        binding.textDescription.text = item.description
    }
}
