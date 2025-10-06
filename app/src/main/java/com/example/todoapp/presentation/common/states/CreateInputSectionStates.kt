package com.example.todoapp.presentation.common.states

data class CreateInputSectionStates(
    val userName: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val passwordVisible: Boolean,
    val confirmPasswordVisible: Boolean,

    val usernameError: String?,
    val emailError: String?,
    val passwordError: String?,
    val confirmPasswordError: String?
)
