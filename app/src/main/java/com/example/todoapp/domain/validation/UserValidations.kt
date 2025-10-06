package com.example.todoapp.domain.validation

import com.example.todoapp.presentation.common.MIN_LENGTH_FOR_PASSWORD
import com.example.todoapp.presentation.common.MIN_LENGTH_FOR_USERNAME
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserValidations @Inject constructor() {

    fun validateUsername(username: String): Boolean {
        return username.length >= MIN_LENGTH_FOR_USERNAME
    }

    fun validateEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePassword(password: String): Boolean {
        return password.length >= MIN_LENGTH_FOR_PASSWORD
    }

    fun validateConfirmPassword(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }
}
