package com.example.todoapp.presentation.common.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.todoapp.R
import com.example.todoapp.presentation.common.states.PasswordTextFieldState

@Composable
fun UserNameTextField(
    userName: String,
    onUserNameChange: (String) -> Unit,
    errorText: String? = null,
    modifier: Modifier
) {
    OutlinedTextField(
        label = {
            Text(stringResource(R.string.username))
        },
        value = userName,
        onValueChange = { onUserNameChange(it) },
        placeholder = {
            Text(stringResource(R.string.enter_username))
        },
        isError = errorText != null,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        maxLines = 1,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Person Icon"
            )
        },
        supportingText = {
            Text(errorText ?: "")
        },
        modifier = modifier.fillMaxWidth()

    )
}

@Composable
fun EmailTextField(
    email: String,
    onEmailChange: (String) -> Unit,
    errorText: String? = null,
    modifier: Modifier
) {
    OutlinedTextField(
        label = {
            Text(stringResource(R.string.email))
        },
        value = email,
        onValueChange = { onEmailChange(it) },
        placeholder = {
            Text(stringResource(R.string.enter_email))
        },
        isError = errorText != null,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        ),
        maxLines = 1,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email Icon"
            )
        },
        supportingText = {
            Text(errorText ?: "")
        },
        modifier = modifier.fillMaxWidth()

    )
}

@Composable
fun PasswordTextField(
    state: PasswordTextFieldState,
    onPasswordChange: (String) -> Unit,
    onToggleVisibilityClick: () -> Unit,
    modifier: Modifier
) {
    OutlinedTextField(
        label = {
            Text(stringResource(R.string.password))
        },
        value = state.password,
        onValueChange = { onPasswordChange(it) },
        placeholder = {
            Text(stringResource(R.string.enter_password))
        },
        isError = state.errorText != null,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        maxLines = 1,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Lock Icon"
            )
        },
        supportingText = {
            Text(state.errorText ?: "")
        },
        visualTransformation = if (state.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (state.passwordVisible) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }

            val description = if (state.passwordVisible) {
                "Hide Password"
            } else {
                "Show Password"
            }

            IconButton(
                onClick = {
                    onToggleVisibilityClick()
                }
            ) { Icon(imageVector = image, description) }
        },
        modifier = modifier.fillMaxWidth()

    )
}

@Composable
fun ConfirmPasswordTextField(
    state: PasswordTextFieldState,
    onConfirmPasswordChange: (String) -> Unit,
    onToggleVisibilityClick: () -> Unit,
    modifier: Modifier
) {
    OutlinedTextField(
        label = {
            Text(stringResource(R.string.confirm_password))
        },
        value = state.password,
        onValueChange = { onConfirmPasswordChange(it) },
        placeholder = {
            Text(stringResource(R.string.enter_password_again))
        },
        isError = state.errorText != null,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        maxLines = 1,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Lock Icon"
            )
        },
        supportingText = {
            Text(state.errorText ?: "")
        },
        visualTransformation = if (state.passwordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            val image = if (state.passwordVisible) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }

            val description = if (state.passwordVisible) {
                "Hide Password"
            } else {
                "Show Password"
            }

            IconButton(
                onClick = {
                    onToggleVisibilityClick()
                }
            ) { Icon(imageVector = image, description) }
        },
        modifier = modifier.fillMaxWidth()

    )
}
