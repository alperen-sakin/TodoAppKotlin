package com.example.todoapp.presentation.common.states

import com.example.todoapp.presentation.common.components.PasswordField

data class CreateInputSectionEvents(
    val onUserNameChange: (String) -> Unit,
    val onEmailChange: (String) -> Unit,
    val onPasswordChange: (String) -> Unit,
    val onConfirmPasswordChange: (String) -> Unit,
    val onTogglePasswordVisibility: (PasswordField) -> Unit,
)
