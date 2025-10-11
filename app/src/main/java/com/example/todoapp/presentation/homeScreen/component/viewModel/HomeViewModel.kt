package com.example.todoapp.presentation.homeScreen.component.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.domain.model.Task
import com.example.todoapp.domain.model.TaskPriority
import com.example.todoapp.domain.useCase.AddTaskUseCase
import com.example.todoapp.domain.useCase.DeleteUseCase
import com.example.todoapp.domain.useCase.GetAllTasksUseCase
import com.example.todoapp.presentation.homeScreen.component.TaskUiItem
import com.example.todoapp.presentation.homeScreen.component.toTask
import com.example.todoapp.util.toLocalDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.sql.SQLException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTasksUseCase: GetAllTasksUseCase,
    private val deleteUseCase: DeleteUseCase,
    private val updateUseCase: AddTaskUseCase
) : ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeViewModelState())
    val homeUiState: StateFlow<HomeViewModelState> = _homeUiState.asStateFlow()

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            loadTasks()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadTasks() {
        viewModelScope.launch {
            _homeUiState.update { it.copy(isLoading = true) }

            getTasksUseCase.invoke().collect { taskList: List<Task> ->
                val uiList = taskList.map { task ->
                    TaskUiItem(
                        id = task.id ?: 0,
                        title = task.title,
                        description = task.description,
                        dueDate = task.dueDate.toLocalDateTime(),
                        isDone = task.isDone,
                        priority = TaskPriority.valueOf(task.priority.uppercase())
                    )
                }

                _homeUiState.update { currentState ->
                    currentState.copy(
                        tasks = uiList,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onSegmentSelected(index: Int) {
        _homeUiState.update { it.copy(selectedIndex = index) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onDeleteTask(taskId: Int) {
        viewModelScope.launch {
            val taskToDelete = _homeUiState.value.tasks.find { it.id == taskId }

            if (taskToDelete != null) {
                try {
                    val taskToDeleteDomain = taskToDelete.toTask()

                    deleteUseCase(taskToDeleteDomain)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else {
                Log.w("DeleteTask", "Silinecek görev (ID: $taskId) bulunamadı.")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onDoneTask(taskId: Int) {
        viewModelScope.launch {
            val currentTaskUi = _homeUiState.value.tasks.find { it.id == taskId }

            if (currentTaskUi != null) {
                val newIsDone = !currentTaskUi.isDone

                val taskToUpdateDomain = currentTaskUi.toTask().copy(
                    isDone = newIsDone
                )

                try {
                    updateUseCase(taskToUpdateDomain)
                } catch (e: SQLException) {
                    e.printStackTrace()
                }
            }
        }
    }
}
