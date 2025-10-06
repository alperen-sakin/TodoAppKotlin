package com.example.todoapp.presentation.createScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.R
import com.example.todoapp.domain.repository.UserDetailsRepository
import com.example.todoapp.domain.validation.UserValidations
import com.example.todoapp.presentation.common.components.PasswordField
import com.example.todoapp.util.StringResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class CreateEvent {
    object NavigateToLogin : CreateEvent()
}

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val stringResourceProvider: StringResourceProvider,
    private val userDetailsRepository: UserDetailsRepository,
    private val userValidations: UserValidations
) : ViewModel() {

    private val _createUiState = MutableStateFlow(CreateViewModelState())
    val createUiState: StateFlow<CreateViewModelState> = _createUiState.asStateFlow()

    fun onUserNameChange(userName: String) {
        _createUiState.update {
            it.copy(userName = userName)
        }
    }

    fun onEmailChange(email: String) {
        _createUiState.update {
            it.copy(email = email)
        }
    }

    fun onPasswordChange(password: String) {
        _createUiState.update {
            it.copy(password = password)
        }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _createUiState.update {
            it.copy(confirmPassword = confirmPassword)
        }
    }

    fun togglePasswordVisibility(field: PasswordField) {
        _createUiState.update { currentState ->
            when (field) {
                PasswordField.PASSWORD -> currentState.copy(isPasswordVisible = !currentState.isPasswordVisible)
                PasswordField.CONFIRM_PASSWORD -> currentState.copy(
                    isConfirmPasswordVisible = !currentState.isConfirmPasswordVisible
                )
            }
        }
    }

    fun onRegisterClick() {
        val state = _createUiState.value

        val isUsernameValid = userValidations.validateUsername(state.userName)
        val isEmailValid = userValidations.validateEmail(state.email)
        val isPasswordValid = userValidations.validatePassword(state.password)
        val isConfirmPasswordValid = userValidations.validateConfirmPassword(
            password = state.password,
            confirmPassword = state.confirmPassword
        )

        _createUiState.update {
            it.copy(
                userNameError = if (isUsernameValid) {
                    null
                } else {
                    stringResourceProvider.getString(
                        R.string.username_error
                    )
                },
                emailError = if (isEmailValid) null else stringResourceProvider.getString(R.string.email_error),
                passwordError = if (isPasswordValid) {
                    null
                } else {
                    stringResourceProvider.getString(
                        R.string.password_error
                    )
                },
                confirmPasswordError = if (isConfirmPasswordValid) {
                    null
                } else {
                    stringResourceProvider.getString(
                        R.string.confirm_password_error
                    )
                }
            )
        }

        val allFieldsValid = isUsernameValid && isEmailValid && isPasswordValid && isConfirmPasswordValid

        if (allFieldsValid) {
            onRegister()
        }
    }

    private val _eventChannel = Channel<CreateEvent>()

    // UI'ın bu olayları dinlemesi için Flow
    val events = _eventChannel.receiveAsFlow()

    private fun onRegister() {
        val state = _createUiState.value

        viewModelScope.launch {
            userDetailsRepository.saveUserDetails(
                username = state.userName,
                email = state.email,
                password = state.password
            )

            _eventChannel.send(CreateEvent.NavigateToLogin)
        }
    }
}
