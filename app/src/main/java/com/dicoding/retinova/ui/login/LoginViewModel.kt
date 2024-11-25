package com.dicoding.retinova.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.retinova.data.UserRepository
import com.dicoding.retinova.data.pref.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    fun login(email: String, password: String) {
        if (email == "ayam" && password == "ayam") {
            viewModelScope.launch {
                userRepository.saveSession(UserModel(email, "exampleToken", true))
                _isLoggedIn.value = true
            }
        } else {
            _isLoggedIn.value = false
        }
    }
}
