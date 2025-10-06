package com.example.todoapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.presentation.createScreen.CreateScreen
import com.example.todoapp.presentation.homeScreen.HomeScreen
import com.example.todoapp.presentation.loginScreen.LoginScreen

@Composable
fun Navigation(
    initialRoute: String
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = initialRoute) {
        composable("login_screen") {
            LoginScreen(navController = navController)
        }
        composable("home_screen") {
            HomeScreen()
        }
        composable("create_screen") {
            CreateScreen(navController = navController)
        }
    }
}
