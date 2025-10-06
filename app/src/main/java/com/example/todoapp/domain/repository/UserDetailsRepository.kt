package com.example.todoapp.domain.repository

import com.example.todoapp.domain.model.UserDetails
import kotlinx.coroutines.flow.Flow

interface UserDetailsRepository {

    suspend fun saveUserDetails(username: String, email: String, password: String)

    fun getUserDetails(): Flow<UserDetails>

    suspend fun clearUserDetails()
}
