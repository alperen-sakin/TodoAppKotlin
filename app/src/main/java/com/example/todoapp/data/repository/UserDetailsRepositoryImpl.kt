package com.example.todoapp.data.repository

import com.example.todoapp.data.local.UserDetailsDataSource
import com.example.todoapp.domain.model.UserDetails
import com.example.todoapp.domain.repository.UserDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDetailsRepositoryImpl @Inject constructor(
    private val localDataSource: UserDetailsDataSource
) : UserDetailsRepository {
    override suspend fun saveUserDetails(username: String, email: String, password: String) {
        localDataSource.saveUserDetails(username, email, password)
    }

    override fun getUserDetails(): Flow<UserDetails> {
        return localDataSource.getUserDetails()
            .map { userDetailsPreferences ->
                userDetailsPreferences.toUserDetails()
            }
    }

    override suspend fun clearUserDetails() {
        localDataSource.clearUserDetails()
    }
}
