package com.example.fillfullment.ui.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_data")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val PASSWORD_KEY = stringPreferencesKey("password")
        private val ID_KEY = intPreferencesKey("id")
    }

    val getUsernameToken: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USERNAME_KEY] ?: ""
    }

    val getPasswordToken: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[PASSWORD_KEY] ?: ""
    }

    val getIdToken: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[ID_KEY] ?: 0
    }

    suspend fun saveUserData(username: String, password: String, id: Int) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
            preferences[PASSWORD_KEY] = password
            preferences[ID_KEY] = id
        }
    }
}