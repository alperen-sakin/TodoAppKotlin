package com.example.todoapp.presentation.loginScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.R
import com.example.todoapp.domain.repository.UserDetailsRepository
import com.example.todoapp.presentation.common.MIN_LENGTH_FOR_PASSWORD
import com.example.todoapp.presentation.common.MIN_LENGTH_FOR_USERNAME
import com.example.todoapp.util.StringResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoginEvent {
    object NavigateToHome : LoginEvent()
    data class ShowToast(val message: String) : LoginEvent()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val stringResourceProvider: StringResourceProvider,
    private val userDetailsRepository: UserDetailsRepository
) : ViewModel() {

    private val _eventChannel = Channel<LoginEvent>()
    val events = _eventChannel.receiveAsFlow()

    private val _loginUiState = MutableStateFlow(LoginViewModelState())
    val loginUiState: StateFlow<LoginViewModelState> = _loginUiState.asStateFlow()

    fun onUsernameChange(username: String) {
        _loginUiState.update {
            it.copy(username = username)
        }
    }

    fun onPasswordChange(password: String) {
        _loginUiState.update {
            it.copy(password = password)
        }
    }

    fun togglePasswordVisibility() {
        _loginUiState.update { currentState ->
            currentState.copy(isPasswordVisible = !currentState.isPasswordVisible)
        }
    }

    fun onLoginClick() {
        val state = _loginUiState.value

        val isUsernameValid = validateUsername(state.username)
        val isPasswordValid = validatePassword(state.password)

        _loginUiState.update {
            it.copy(
                usernameError = if (isUsernameValid) {
                    null
                } else {
                    stringResourceProvider.getString(
                        R.string.username_error
                    )
                },
                passwordError = if (isPasswordValid) null else stringResourceProvider.getString(R.string.password_error)
            )
        }

        if (isUsernameValid && isPasswordValid) {
            loginAccount()
        }
    }

    private fun loginAccount() {
        val state = _loginUiState.value

        viewModelScope.launch {
            if (isValidUser(username = state.username, password = state.password)) {
                _eventChannel.send(LoginEvent.NavigateToHome)
            } else {
                _eventChannel.send(LoginEvent.ShowToast(stringResourceProvider.getString(R.string.account_not_found)))
            }
        }
    }

    private suspend fun isValidUser(username: String, password: String): Boolean {
        val userDetails = userDetailsRepository.getUserDetails().first()

        val usernameMatch = username == userDetails.username
        val passwordMatch = password == userDetails.password

        return usernameMatch && passwordMatch
    }

    private fun validateUsername(username: String): Boolean {
        return username.length >= MIN_LENGTH_FOR_USERNAME
    }

    private fun validatePassword(password: String): Boolean {
        return password.length >= MIN_LENGTH_FOR_PASSWORD
    }
}
