package com.example.todoapp.presentation.createTask.components.states

import com.example.todoapp.domain.model.TaskPriority

data class CreateTaskInputStates(
    val taskTitle: String,
    val taskTitleError: String?,
    val description: String,

    val currentDate: String,
    val dateError: String?,
    val isDatePickerVisible: Boolean,

    val currentTime: String,
    val timeError: String?,
    val isTimePickerVisible: Boolean,

    val prioritySelected: TaskPriority,

)
