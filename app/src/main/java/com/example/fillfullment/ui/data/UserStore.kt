package com.example.fillfullment.ui.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_data")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val PASSWORD_KEY = stringPreferencesKey("password")
    }

    val getUsernameToken: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USERNAME_KEY] ?: ""
    }

    val getPasswordToken: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[PASSWORD_KEY] ?: ""
    }

    suspend fun saveUserData(username: String, password: String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
            preferences[PASSWORD_KEY] = password
        }
    }
}