package com.example.todoapp.presentation.createTask.components.states

import com.example.todoapp.domain.model.TaskPriority

data class CreateTaskInputEvents(
    val onTaskNameChange: (String) -> Unit,
    val onDescriptionChange: (String) -> Unit,

    val onDatePickerToggle: (Boolean) -> Unit,
    val onDateSelected: (String) -> Unit,

    val onTimeSelected: (Int, Int) -> Unit,
    val onTimepickerToggleVisibility: (Boolean) -> Unit,

    val onPriorityChange: (TaskPriority) -> Unit
)
