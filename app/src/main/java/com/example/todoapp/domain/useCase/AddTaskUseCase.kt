package com.example.todoapp.domain.useCase

import com.example.todoapp.domain.model.Task
import com.example.todoapp.domain.repository.TaskRepository
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(task: Task) {
        taskRepository.addTask(task)
    }
}
