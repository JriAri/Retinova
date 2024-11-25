package com.dicoding.retinova.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
    private val USER_TOKEN_KEY = stringPreferencesKey("user_token")
    private val IS_LOGIN_KEY = stringPreferencesKey("is_login")

    suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[USER_EMAIL_KEY] = user.email
            preferences[USER_TOKEN_KEY] = user.token
            preferences[IS_LOGIN_KEY] = user.isLogin.toString()
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            val email = preferences[USER_EMAIL_KEY] ?: ""
            val token = preferences[USER_TOKEN_KEY] ?: ""
            val isLogin = preferences[IS_LOGIN_KEY]?.toBoolean() ?: false
            UserModel(email, token, isLogin)
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.remove(USER_EMAIL_KEY)
            preferences.remove(USER_TOKEN_KEY)
            preferences.remove(IS_LOGIN_KEY)
        }
    }

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

        @Volatile
        private var INSTANCE: UserPreference? = null

        fun getInstance(context: Context): UserPreference {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserPreference(context.dataStore).also { INSTANCE = it }
            }
        }
    }
}
