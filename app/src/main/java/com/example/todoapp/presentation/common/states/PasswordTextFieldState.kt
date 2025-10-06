package com.example.todoapp.presentation.common.states

data class PasswordTextFieldState(
    val password: String,
    val passwordVisible: Boolean,
    val errorText: String? = null,

)
