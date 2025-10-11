package com.example.todoapp.presentation.homeScreen.component.viewModel

import com.example.todoapp.presentation.homeScreen.component.TaskUiItem

data class HomeViewModelState(
    val tasks: List<TaskUiItem> = emptyList(),
    val isLoading: Boolean = true,
    val selectedIndex: Int = 0
)
