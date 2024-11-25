package com.dicoding.retinova.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.retinova.R
import com.dicoding.retinova.data.UserRepository
import com.dicoding.retinova.data.pref.UserPreference
import com.dicoding.retinova.databinding.ActivityHomeBinding
import com.dicoding.retinova.ui.dashboard.DashboardFragment
import com.dicoding.retinova.ui.home.HomeFragment
import com.dicoding.retinova.ui.notifications.NotificationsFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userRepository = UserRepository.getInstance(UserPreference.getInstance(this))

        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> replaceFragment(HomeFragment())
                R.id.navigation_detect -> replaceFragment(DashboardFragment())
                R.id.navigation_person -> replaceFragment(NotificationsFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
