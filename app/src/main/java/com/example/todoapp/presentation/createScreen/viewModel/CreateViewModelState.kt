package com.example.todoapp.presentation.createScreen.viewModel

data class CreateViewModelState(
    val userName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",

    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,

    val userNameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,

    val isSuccessful: Boolean = false
)
