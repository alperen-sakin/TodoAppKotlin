package com.example.todoapp.data.repository

import com.example.todoapp.data.local.dao.TaskDao
import com.example.todoapp.data.mapper.toDomain
import com.example.todoapp.data.mapper.toEntity
import com.example.todoapp.domain.model.Task
import com.example.todoapp.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {
    override suspend fun addTask(task: Task) {
        taskDao.insertTask(task.toEntity())
    }

    override fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task.toEntity())
    }
}
