package com.example.todoapp.presentation.createTask

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.presentation.common.GradiantBackgroundBox
import com.example.todoapp.presentation.createTask.components.DatePickerTextField
import com.example.todoapp.presentation.createTask.components.DescriptionTextField
import com.example.todoapp.presentation.createTask.components.PrioritySelector
import com.example.todoapp.presentation.createTask.components.TaskTitleTextField
import com.example.todoapp.presentation.createTask.components.TimePickerTextField
import com.example.todoapp.presentation.createTask.components.states.CreateTaskInputEvents
import com.example.todoapp.presentation.createTask.components.states.CreateTaskInputStates
import com.example.todoapp.presentation.createTask.viewModel.CreateTaskViewModel
import com.example.todoapp.presentation.createTask.viewModel.UiEvent
import com.example.todoapp.ui.theme.MediumTurquoise
import com.example.todoapp.ui.theme.Poppins
import com.example.todoapp.ui.theme.TextColor

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateTaskScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: CreateTaskViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                is UiEvent.NavigateBack -> {
                    navController.popBackStack()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onSaveClick() },
                containerColor = MediumTurquoise,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done"
                )
            }
        }
    ) { innerPadding ->
        CreateTaskScreenContent(viewModel, modifier.padding(innerPadding))
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun CreateTaskScreenContent(
    viewModel: CreateTaskViewModel,
    modifier: Modifier
) {
    val createTaskUiState by viewModel.createTaskUiState.collectAsState()
    GradiantBackgroundBox {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 21.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Create Task",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = TextColor,
                fontFamily = Poppins,
                textAlign = TextAlign.Center,
                modifier = modifier.padding(top = 100.dp)
            )

            Spacer(Modifier.height(90.dp))

            CreateTaskInputSection(
                state = CreateTaskInputStates(
                    taskTitle = createTaskUiState.taskTitle,
                    taskTitleError = createTaskUiState.titleError,
                    description = createTaskUiState.description,
                    currentDate = createTaskUiState.currentDate,
                    isDatePickerVisible = createTaskUiState.isDatePickerVisible,
                    currentTime = createTaskUiState.currentTime,
                    isTimePickerVisible = createTaskUiState.isTimePickerVisible,
                    prioritySelected = viewModel.priority.value,
                    dateError = createTaskUiState.dateError,
                    timeError = createTaskUiState.timeError
                ),
                events = CreateTaskInputEvents(
                    onTaskNameChange = viewModel::onTaskNameChange,
                    onDescriptionChange = viewModel::onTaskDescriptionChange,
                    onDatePickerToggle = viewModel::onDatePickerToggle,
                    onDateSelected = viewModel::onDateSelected,
                    onTimeSelected = viewModel::onTimeSelected,
                    onTimepickerToggleVisibility = viewModel::onTimePickerToggle,
                    onPriorityChange = viewModel::setPriority
                ),
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateTaskInputSection(
    state: CreateTaskInputStates,
    events: CreateTaskInputEvents,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        TaskTitleTextField(
            taskTitle = state.taskTitle,
            onTaskTitleChange = events.onTaskNameChange,
            errorText = state.taskTitleError,
            modifier = Modifier
        )

        DescriptionTextField(
            description = state.description,
            onDescriptionChange = events.onDescriptionChange,
            errorText = null,
            modifier = Modifier
        )

        DatePickerTextField(
            currentDate = state.currentDate,
            isDatePickerVisible = state.isDatePickerVisible,
            onDatePickerToggle = events.onDatePickerToggle,
            onDateSelected = events.onDateSelected,
            errorText = state.dateError
        )

        TimePickerTextField(
            currentTime = state.currentTime,
            isTimePickerVisible = state.isTimePickerVisible,
            onTimePickerToggle = events.onTimepickerToggleVisibility,
            onTimeSelected = events.onTimeSelected,
            errorText = state.timeError
        )

        Spacer(Modifier.height(25.dp))

        PrioritySelector(
            selectedPriority = state.prioritySelected,
            onPriorityChange = events.onPriorityChange
        )
    }
}

@Preview
@Composable
fun CreateTaskScreen() {
    val navController = rememberNavController()
    CreateTaskScreen()
}
