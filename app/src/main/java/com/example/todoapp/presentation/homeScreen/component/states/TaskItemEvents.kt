package com.example.todoapp.presentation.homeScreen.component.states

data class TaskItemEvents(
    val onDoneClick: () -> Unit,
    val onDeleteClick: () -> Unit
)
