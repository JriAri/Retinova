package com.dicoding.retinova.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.retinova.data.UserRepository
import com.dicoding.retinova.data.pref.UserModel
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _isRegistered = MutableLiveData<Boolean>()
    val isRegistered: LiveData<Boolean> = _isRegistered

    fun register(name: String, email: String, password: String, confirmPassword: String) {
        if (!validateRegistration(name, email, password, confirmPassword)) {
            _isRegistered.value = false
            return
        }
        viewModelScope.launch {
            userRepository.saveSession(UserModel(email, "exampleToken", true))
            _isRegistered.value = true
        }
    }

    private fun validateRegistration(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false
        }
        if (password.length < 6 || password != confirmPassword) {
            return false
        }
        return true
    }
}
