package com.example.todoapp.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.presentation.createScreen.CreateScreen
import com.example.todoapp.presentation.createTask.CreateTaskScreen
import com.example.todoapp.presentation.homeScreen.HomeScreen
import com.example.todoapp.presentation.loginScreen.LoginScreen

@RequiresApi(Build.VERSION_CODES.O)
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
            HomeScreen(navController = navController)
        }
        composable("create_screen") {
            CreateScreen(navController = navController)
        }
        composable("create_task_screen") {
            CreateTaskScreen(navController = navController)
        }
    }
}
