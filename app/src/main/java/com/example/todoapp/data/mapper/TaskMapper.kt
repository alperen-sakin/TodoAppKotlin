package com.example.todoapp.data.mapper

import com.example.todoapp.data.local.entity.TaskEntity
import com.example.todoapp.domain.model.Task

fun TaskEntity.toDomain(): Task {
    return Task(
        this.id,
        this.title,
        this.description,
        this.dueDate,
        this.isDone,
        this.priority

    )
}

fun Task.toEntity(): TaskEntity {
    return TaskEntity(
        this.id,
        this.title,
        this.description,
        this.dueDate,
        this.isDone,
        this.priority
    )
}
