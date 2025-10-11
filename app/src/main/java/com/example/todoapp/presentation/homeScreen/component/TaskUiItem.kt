package com.example.todoapp.presentation.homeScreen.component

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.todoapp.domain.model.Task
import com.example.todoapp.domain.model.TaskPriority
import java.time.LocalDateTime
import java.time.ZoneId

data class TaskUiItem(
    val id: Int,
    val title: String,
    val description: String,
    val dueDate: LocalDateTime,
    val isDone: Boolean,
    val priority: TaskPriority
)

@RequiresApi(Build.VERSION_CODES.O)
fun TaskUiItem.toTask(): Task {
    return Task(
        id = this.id,
        title = this.title,
        description = this.description,
        dueDate = this.dueDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
        isDone = this.isDone,
        priority = this.priority.name
    )
}
