package com.example.todoapp.presentation.createTask.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.R
import com.example.todoapp.domain.model.Task
import com.example.todoapp.domain.model.TaskPriority
import com.example.todoapp.domain.useCase.AddTaskUseCase
import com.example.todoapp.domain.validation.TaskInfoValidation
import com.example.todoapp.util.StringResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.sql.SQLDataException
import java.text.SimpleDateFormat
import java.time.format.DateTimeParseException
import java.util.Date
import java.util.Locale
import javax.inject.Inject

sealed class UiEvent {
    data class ShowToast(val message: String) : UiEvent()
    object NavigateBack : UiEvent()
}

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val stringProvider: StringResourceProvider,
    private val taskInfoValidation: TaskInfoValidation,
    private val addTaskUseCase: AddTaskUseCase
) : ViewModel() {

    private val _createTaskUiState = MutableStateFlow(CreateTaskViewModelState())
    val createTaskUiState: StateFlow<CreateTaskViewModelState> = _createTaskUiState.asStateFlow()

    private val _priority = mutableStateOf(TaskPriority.MEDIUM)
    val priority: State<TaskPriority> = _priority

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun setPriority(newPriority: TaskPriority) {
        _priority.value = newPriority
    }

    fun onTaskNameChange(taskName: String) {
        _createTaskUiState.update {
            it.copy(taskTitle = taskName)
        }
    }

    fun onTaskDescriptionChange(description: String) {
        _createTaskUiState.update {
            it.copy(description = description)
        }
    }

    fun onDateSelected(newDate: String) {
        _createTaskUiState.update {
            it.copy(currentDate = newDate)
        }
    }

    fun onDatePickerToggle(isVisible: Boolean) {
        _createTaskUiState.update {
            it.copy(isDatePickerVisible = isVisible)
        }
    }

    fun onTimeSelected(hour: Int, minute: Int) {
        _createTaskUiState.update {
            it.copy(currentTime = "$hour:$minute")
        }
    }

    fun onTimePickerToggle(isVisible: Boolean) {
        _createTaskUiState.update {
            it.copy(isTimePickerVisible = isVisible)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSaveClick() {
        val state = _createTaskUiState.value

        val isTaskTitleValid = taskInfoValidation.validateTitle(state.taskTitle)
        val isDateValid = taskInfoValidation.validateDate(state.currentDate)
        val isTimeValid = taskInfoValidation.validateTime(state.currentTime)

        _createTaskUiState.update {
            it.copy(
                titleError = if (isTaskTitleValid) {
                    null
                } else {
                    stringProvider.getString(R.string.title_error)
                },
                dateError = if (isDateValid) {
                    null
                } else {
                    stringProvider.getString(R.string.date_error)
                },
                timeError = if (isTimeValid) {
                    null
                } else {
                    stringProvider.getString(R.string.time_error)
                }
            )
        }

        val allFieldValid = isTimeValid && isDateValid && isTimeValid

        if (allFieldValid) {
            onSave()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onSave() {
        val state = _createTaskUiState.value

        val dateString = state.currentDate
        val timeString = state.currentTime

        val dateTimeString = "$dateString $timeString"

        val formatter = SimpleDateFormat("MM dd, yyyy HH:mm", Locale.getDefault())
        val combineDate = try {
            formatter.parse(dateTimeString)
        } catch (e: DateTimeParseException) {
            Date()
        }

        val dueLong = combineDate?.time ?: System.currentTimeMillis()

        val newTask = Task(
            id = null,
            title = state.taskTitle,
            description = state.description,
            dueDate = dueLong,
            priority = priority.value.name,
            isDone = false
        )

        viewModelScope.launch {
            try {
                addTaskUseCase(newTask)

                _uiEvent.send(UiEvent.ShowToast("Task Successful Saved"))

                _uiEvent.send(UiEvent.NavigateBack)
            } catch (e: SQLDataException) {
                e.printStackTrace()
                _uiEvent.send(UiEvent.ShowToast("Task Failed to Save"))
            }
        }
    }
}
