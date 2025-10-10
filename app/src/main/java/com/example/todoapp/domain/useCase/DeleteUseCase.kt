package com.example.todoapp.domain.useCase

import com.example.todoapp.domain.model.Task
import com.example.todoapp.domain.repository.TaskRepository
import javax.inject.Inject

class DeleteUseCase @Inject constructor(
    private val repository: TaskRepository

) {
    suspend operator fun invoke(task: Task) {
        repository.deleteTask(task)
    }
}
