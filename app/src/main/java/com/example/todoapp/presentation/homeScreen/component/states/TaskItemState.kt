package com.example.todoapp.presentation.homeScreen.component.states

import androidx.compose.ui.graphics.Color
import java.time.LocalDateTime

data class TaskItemState(
    val taskTitle: String,
    val priorityText: String,
    val description: String,
    val dueDate: LocalDateTime,
    val priorityTextColor: Color,
    val priorityBackgroundColor: Color,
    val isTaskDone: Boolean,
)
