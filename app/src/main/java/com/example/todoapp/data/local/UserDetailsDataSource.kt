package com.example.todoapp.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.todoapp.data.model.UserDetailsPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDetailsDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private object PreferencesKeys {
        val USERNAME = stringPreferencesKey("user_name")
        val EMAIL = stringPreferencesKey("user_email")
        val PASSWORD = stringPreferencesKey("user_password")
    }

    suspend fun saveUserDetails(username: String, email: String, password: String) {
        dataStore.edit { details ->
            details[PreferencesKeys.USERNAME] = username
            details[PreferencesKeys.EMAIL] = email
            details[PreferencesKeys.PASSWORD] = password
        }
    }

    fun getUserDetails(): Flow<UserDetailsPreferences> {
        return dataStore.data
            .map { preferences ->
                UserDetailsPreferences(
                    username = preferences[PreferencesKeys.USERNAME],
                    email = preferences[PreferencesKeys.EMAIL],
                    password = preferences[PreferencesKeys.PASSWORD]
                )
            }
    }

    suspend fun clearUserDetails() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.USERNAME)
            preferences.remove(PreferencesKeys.EMAIL)
            preferences.remove(PreferencesKeys.PASSWORD)
        }
    }
}
