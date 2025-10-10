package com.example.todoapp.domain.model

data class Task(
    val id: Int? = null,
    val title: String,
    val description: String,
    val dueDate: Long,
    val isDone: Boolean = false,
    val priority: String

)
