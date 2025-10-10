package com.example.todoapp.presentation.createTask.viewModel

data class CreateTaskViewModelState(
    val taskTitle: String = "",
    val titleError: String? = null,
    val description: String = "",

    val currentDate: String = "",
    val dateError: String? = null,
    val isDatePickerVisible: Boolean = false,

    val currentTime: String = "",
    val timeError: String? = null,
    val isTimePickerVisible: Boolean = false

)
