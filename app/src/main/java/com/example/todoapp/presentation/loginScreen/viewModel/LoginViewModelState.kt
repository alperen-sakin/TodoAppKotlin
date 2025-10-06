package com.example.todoapp.presentation.loginScreen.viewModel

data class LoginViewModelState(
    val username: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val usernameError: String? = null,
    val passwordError: String? = null,
)
