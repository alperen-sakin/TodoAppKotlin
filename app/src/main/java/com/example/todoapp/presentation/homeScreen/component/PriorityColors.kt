package com.example.todoapp.presentation.homeScreen.component

import androidx.compose.ui.graphics.Color
import com.example.todoapp.domain.model.TaskPriority
import com.example.todoapp.ui.theme.PriorityHighBackground
import com.example.todoapp.ui.theme.PriorityHighText
import com.example.todoapp.ui.theme.PriorityLowBackground
import com.example.todoapp.ui.theme.PriorityLowText
import com.example.todoapp.ui.theme.PriorityMediumBackground
import com.example.todoapp.ui.theme.PriorityMediumText

fun getPriorityColors(priority: TaskPriority): Pair<Color, Color> {
    return when (priority) {
        TaskPriority.LOW -> Pair(PriorityLowText, PriorityLowBackground)
        TaskPriority.MEDIUM -> Pair(PriorityMediumText, PriorityMediumBackground)
        TaskPriority.HIGH -> Pair(PriorityHighText, PriorityHighBackground)
    }
}
