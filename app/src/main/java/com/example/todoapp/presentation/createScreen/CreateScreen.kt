package com.example.todoapp.presentation.createScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.R
import com.example.todoapp.presentation.common.GradiantBackgroundBox
import com.example.todoapp.presentation.common.components.ConfirmPasswordTextField
import com.example.todoapp.presentation.common.components.EmailTextField
import com.example.todoapp.presentation.common.components.PasswordField
import com.example.todoapp.presentation.common.components.PasswordTextField
import com.example.todoapp.presentation.common.components.UserNameTextField
import com.example.todoapp.presentation.common.states.CreateInputSectionEvents
import com.example.todoapp.presentation.common.states.CreateInputSectionStates
import com.example.todoapp.presentation.common.states.PasswordTextFieldState
import com.example.todoapp.presentation.createScreen.viewModel.CreateEvent
import com.example.todoapp.presentation.createScreen.viewModel.CreateViewModel
import com.example.todoapp.ui.theme.MediumTurquoise
import com.example.todoapp.ui.theme.Poppins
import com.example.todoapp.ui.theme.TextColor

@Composable
fun CreateScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: CreateViewModel = hiltViewModel()
) {
    val createUiState by viewModel.createUiState.collectAsState()
    LaunchedEffect(key1 = Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is CreateEvent.NavigateToLogin -> {
                    navController.navigate("login_screen") {
                        popUpTo("create_screen") {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    GradiantBackgroundBox(
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 21.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            TitleSection(modifier.padding(top = 100.dp))

            Spacer(modifier = Modifier.weight(1f))

            InputSection(
                modifier,
                events = CreateInputSectionEvents(
                    onUserNameChange = viewModel::onUserNameChange,
                    onEmailChange = viewModel::onEmailChange,
                    onPasswordChange = viewModel::onPasswordChange,
                    onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
                    onTogglePasswordVisibility = viewModel::togglePasswordVisibility
                ),
                state = CreateInputSectionStates(
                    userName = createUiState.userName,
                    email = createUiState.email,
                    password = createUiState.password,
                    confirmPassword = createUiState.confirmPassword,
                    passwordVisible = createUiState.isPasswordVisible,
                    confirmPasswordVisible = createUiState.isConfirmPasswordVisible,
                    usernameError = createUiState.userNameError,
                    emailError = createUiState.emailError,
                    passwordError = createUiState.passwordError,
                    confirmPasswordError = createUiState.confirmPasswordError
                )
            )
            Spacer(modifier = Modifier.weight(1f))

            BottomSection(
                modifier = modifier.padding(bottom = 40.dp),
                onSignInClick = { navController.navigate("login_screen") },
                onRegisterClick = viewModel::onRegisterClick
            )
        }
    }
}

@Composable
private fun TitleSection(modifier: Modifier) {
    Text(
        text = stringResource(R.string.welcome_todo_app),
        fontSize = 32.sp,
        color = TextColor,
        fontFamily = Poppins,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = modifier
    )

    Text(
        text = stringResource(R.string.create_account),
        fontSize = 24.sp,
        color = TextColor,
        fontWeight = FontWeight.SemiBold,
        fontFamily = Poppins,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Composable
private fun InputSection(
    modifier: Modifier,
    state: CreateInputSectionStates,
    events: CreateInputSectionEvents

) {
    UserNameTextField(
        userName = state.userName,
        onUserNameChange = { events.onUserNameChange(it) },
        errorText = state.usernameError,
        modifier = modifier
    )
    Spacer(modifier = Modifier.padding(11.dp))

    EmailTextField(
        email = state.email,
        onEmailChange = { events.onEmailChange(it) },
        errorText = state.emailError,
        modifier = modifier
    )
    Spacer(modifier = Modifier.padding(11.dp))

    PasswordTextField(
        state = PasswordTextFieldState(
            password = state.password,
            passwordVisible = state.passwordVisible,
            errorText = state.passwordError
        ),
        onPasswordChange = { events.onPasswordChange(it) },
        onToggleVisibilityClick = { events.onTogglePasswordVisibility(PasswordField.PASSWORD) },
        modifier = modifier
    )
    Spacer(modifier = Modifier.padding(11.dp))

    ConfirmPasswordTextField(
        state = PasswordTextFieldState(
            errorText = state.confirmPasswordError,
            password = state.confirmPassword,
            passwordVisible = state.confirmPasswordVisible
        ),
        onConfirmPasswordChange = { events.onConfirmPasswordChange(it) },
        onToggleVisibilityClick = { events.onTogglePasswordVisibility(PasswordField.CONFIRM_PASSWORD) },
        modifier = modifier
    )
}

@Composable
private fun BottomSection(
    onSignInClick: () -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier
) {
    Button(
        onClick = onRegisterClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MediumTurquoise
        ),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.register),
            fontSize = 20.sp,
            color = Color.White,
            fontFamily = Poppins,
            fontWeight = FontWeight.SemiBold
        )
    }
    Spacer(modifier = Modifier.padding(10.dp))

    val tagSignIn = "SignIn"

    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(color = TextColor)
        ) {
            append(stringResource(R.string.already_have_an_account))
        }

        pushStringAnnotation(tagSignIn, annotation = "Sign In")
        withStyle(
            style = SpanStyle(
                color = MediumTurquoise,
                fontWeight = FontWeight.Bold
            )
        ) {
            append(stringResource(R.string.sign_in))
        }
    }

    ClickableText(
        modifier = modifier,
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(
                tag = tagSignIn,
                start = offset,
                end = offset
            ).firstOrNull()?.let {
                if (it.tag == tagSignIn) {
                    onSignInClick()
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CreateScreenPreview() {
    val navController = rememberNavController()
    CreateScreen(
        navController = navController
    )
}
