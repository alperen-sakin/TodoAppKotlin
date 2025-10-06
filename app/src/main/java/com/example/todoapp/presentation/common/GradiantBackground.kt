package com.example.todoapp.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.todoapp.ui.theme.MediumTurquoise

@Composable
fun GradiantBackgroundBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable () -> Unit

) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colorStops = arrayOf(
                        0.0f to MediumTurquoise,
                        END_COLOR_STOP_RATIO to Color.White
                    )
                )
            ),
        contentAlignment = contentAlignment
    ) {
        content()
    }
}
