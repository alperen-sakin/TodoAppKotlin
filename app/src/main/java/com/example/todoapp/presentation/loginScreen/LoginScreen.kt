package com.example.todoapp.presentation.loginScreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalContext
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
import com.example.todoapp.presentation.common.components.PasswordTextField
import com.example.todoapp.presentation.common.components.UserNameTextField
import com.example.todoapp.presentation.common.states.LoginInputSectionEvents
import com.example.todoapp.presentation.common.states.LoginInputSectionStates
import com.example.todoapp.presentation.common.states.PasswordTextFieldState
import com.example.todoapp.presentation.loginScreen.viewModel.LoginEvent
import com.example.todoapp.presentation.loginScreen.viewModel.LoginViewModel
import com.example.todoapp.ui.theme.MediumTurquoise
import com.example.todoapp.ui.theme.Poppins
import com.example.todoapp.ui.theme.TextColor

@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val loginUiState by viewModel.loginUiState.collectAsState()

    val context = LocalContext.current

    ObserverState(viewModel, context, navController)

    GradiantBackgroundBox(
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 21.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Welcome Back",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = TextColor,
                fontFamily = Poppins,
                textAlign = TextAlign.Center,
                modifier = modifier.padding(top = 100.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            InputSection(
                modifier = modifier,
                state = LoginInputSectionStates(
                    username = loginUiState.username,
                    password = loginUiState.password,
                    usernameError = loginUiState.usernameError,
                    passwordVisible = loginUiState.isPasswordVisible,
                    passwordError = loginUiState.passwordError
                ),
                events = LoginInputSectionEvents(
                    onUsernameChange = viewModel::onUsernameChange,
                    onPasswordChange = viewModel::onPasswordChange,
                    onTogglePasswordVisibility = viewModel::togglePasswordVisibility
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            BottomSection(
                modifier = modifier.padding(bottom = 40.dp),
                onLoginClick = viewModel::onLoginClick,
                onRegisterClick = { navController.navigate("create_screen") }
            )
        }
    }
}

@Composable
private fun ObserverState(
    viewModel: LoginViewModel,
    context: Context,
    navController: NavController
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is LoginEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                is LoginEvent.NavigateToHome -> {
                    navController.navigate("home_screen") {
                        popUpTo("login_screen") {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InputSection(
    modifier: Modifier,
    state: LoginInputSectionStates,
    events: LoginInputSectionEvents
) {
    UserNameTextField(
        userName = state.username,
        onUserNameChange = { events.onUsernameChange(it) },
        errorText = state.usernameError,
        modifier = modifier
    )

    Spacer(modifier = Modifier.height(28.dp))

    PasswordTextField(
        state = PasswordTextFieldState(
            password = state.password,
            passwordVisible = state.passwordVisible,
            errorText = state.passwordError
        ),
        modifier = modifier,
        onPasswordChange = { events.onPasswordChange(it) },
        onToggleVisibilityClick = { events.onTogglePasswordVisibility() }
    )
    Text(
        text = stringResource(R.string.forgot_password),
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold,
        color = MediumTurquoise,
        fontFamily = Poppins,
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.End,
    )
}

@Composable
fun BottomSection(
    modifier: Modifier,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Button(
        onClick = onLoginClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MediumTurquoise
        ),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.sign_in),
            fontSize = 20.sp,
            color = Color.White,
            fontFamily = Poppins,
            fontWeight = FontWeight.SemiBold
        )
    }

    val tagSignIn = "SignIn"

    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(color = TextColor)
        ) {
            append(stringResource(R.string.do_not_have_an_account))
        }

        pushStringAnnotation(tagSignIn, annotation = "Sign In")
        withStyle(
            style = SpanStyle(
                color = MediumTurquoise,
                fontWeight = FontWeight.Bold
            )
        ) {
            append(stringResource(R.string.sign_up))
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
                    onRegisterClick()
                }
            }
        }
    )
}

@Preview
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginScreen(
        navController = navController
    )
}
