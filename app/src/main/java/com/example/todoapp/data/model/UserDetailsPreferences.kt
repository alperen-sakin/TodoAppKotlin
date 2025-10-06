package com.example.todoapp.data.model

import com.example.todoapp.domain.model.UserDetails

data class UserDetailsPreferences(
    val username: String?,
    val email: String?,
    val password: String?
) {
    fun toUserDetails(): UserDetails {
        return UserDetails(
            username = username ?: "",
            email = email ?: "",
            password = password ?: ""
        )
    }
}
