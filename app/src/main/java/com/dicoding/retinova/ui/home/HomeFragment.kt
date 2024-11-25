package com.dicoding.retinova.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dicoding.retinova.data.UserRepository
import com.dicoding.retinova.data.pref.UserPreference
import com.dicoding.retinova.databinding.FragmentHomeBinding
import com.dicoding.retinova.ui.login.LoginActivity
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var userRepository: UserRepository
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        userRepository = UserRepository.getInstance(UserPreference.getInstance(requireContext()))

        binding.logoutButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                userRepository.logout()
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finish()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
