package com.example.todoapp.presentation.common.states

data class LoginInputSectionEvents(
    val onUsernameChange: (String) -> Unit,
    val onPasswordChange: (String) -> Unit,
    val onTogglePasswordVisibility: () -> Unit,
)
