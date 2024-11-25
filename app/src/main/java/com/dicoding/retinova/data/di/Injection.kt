package com.dicoding.retinova.data.di

import android.content.Context
import com.dicoding.retinova.data.UserRepository
import com.dicoding.retinova.data.pref.UserPreference

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context)
        return UserRepository.getInstance(pref)
    }
}