package com.example.todoapp.presentation.common.states

data class LoginInputSectionStates(
    val username: String,
    val password: String,
    val usernameError: String?,
    val passwordVisible: Boolean,
    val passwordError: String?,
)
