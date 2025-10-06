package com.example.todoapp.presentation.homeScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.todoapp.presentation.common.GradiantBackgroundBox

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    GradiantBackgroundBox(
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Home",
            modifier = modifier
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
