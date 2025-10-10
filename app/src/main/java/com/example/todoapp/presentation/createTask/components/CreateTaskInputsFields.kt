@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.todoapp.presentation.createTask.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Task
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.todoapp.R
import com.example.todoapp.domain.model.TaskPriority
import com.example.todoapp.ui.theme.TextColor
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeParseException
import java.util.Date
import java.util.Locale

@Composable
fun TaskTitleTextField(
    taskTitle: String,
    onTaskTitleChange: (String) -> Unit,
    errorText: String? = null,
    modifier: Modifier
) {
    OutlinedTextField(
        label = {
            Text(stringResource(R.string.task_title))
        },
        value = taskTitle,
        onValueChange = { onTaskTitleChange(it) },
        placeholder = {
            Text(stringResource(R.string.enter_task_title))
        },
        isError = errorText != null,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        maxLines = 1,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Task,
                contentDescription = "Task Icon"
            )
        },
        supportingText = {
            Text(errorText ?: "")
        },
        modifier = modifier.fillMaxWidth()

    )
}

@Composable
fun DescriptionTextField(
    description: String,
    onDescriptionChange: (String) -> Unit,
    errorText: String? = null,
    modifier: Modifier
) {
    OutlinedTextField(
        label = {
            Text(stringResource(R.string.description))
        },
        value = description,
        onValueChange = { onDescriptionChange(it) },
        placeholder = {
            Text(stringResource(R.string.enter_task_description))
        },
        isError = errorText != null,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        minLines = 4,
        maxLines = 6,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Description,
                contentDescription = "Description Icon",
            )
        },
        supportingText = {
            Text(errorText ?: "")
        },
        modifier = modifier.fillMaxWidth()

    )
}

@Composable
fun DatePickerTextField(
    errorText: String?,
    currentDate: String,
    isDatePickerVisible: Boolean,
    onDatePickerToggle: (Boolean) -> Unit,
    onDateSelected: (String) -> Unit
) {
    val datePickerState = rememberDatePickerState()
    OutlinedTextField(
        value = currentDate,
        onValueChange = {},
        label = { Text(stringResource(R.string.date)) },
        readOnly = true,
        isError = errorText != null,
        supportingText = { Text(errorText ?: "") },
        trailingIcon = {
            IconButton(
                onClick = { onDatePickerToggle(true) }
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "Date Icon",
                )
            }
        },
        modifier = Modifier.fillMaxWidth()

    )

    if (isDatePickerVisible) {
        DatePickerDialog(
            onDismissRequest = {
                onDatePickerToggle(false)
            },
            confirmButton = {
                Button(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { milis ->
                            val date = Date(milis)
                            val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

                            onDateSelected(formatter.format(date))
                        }
                        onDatePickerToggle(false)
                    }
                ) {
                    Text(text = "Select")
                }
            },
            dismissButton = {
                Button(
                    onClick = { onDatePickerToggle(false) }
                ) {
                    Text(text = "Cancel")
                }
            }

        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimePickerTextField(
    errorText: String?,
    currentTime: String,
    isTimePickerVisible: Boolean,
    onTimePickerToggle: (Boolean) -> Unit,
    onTimeSelected: (hour: Int, minute: Int) -> Unit,

) {
    val initialTime = try {
        LocalTime.parse(currentTime)
    } catch (e: DateTimeParseException) {
        LocalTime.now()
    }

    val timePickerState = rememberTimePickerState(
        initialHour = initialTime.hour,
        initialMinute = initialTime.minute,
        is24Hour = true
    )

    OutlinedTextField(
        value = currentTime,
        onValueChange = {},
        label = { Text(stringResource(R.string.time)) },
        readOnly = true,
        isError = errorText != null,
        supportingText = { Text(errorText ?: "") },
        trailingIcon = {
            IconButton(
                onClick = { onTimePickerToggle(true) }
            ) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = "Time Icon",
                )
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

    IsVisible(isTimePickerVisible, onTimePickerToggle, timePickerState, onTimeSelected)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun IsVisible(
    isTimePickerVisible: Boolean,
    onTimePickerToggle: (Boolean) -> Unit,
    timePickerState: TimePickerState,
    onTimeSelected: (hour: Int, minute: Int) -> Unit
) {
    if (isTimePickerVisible) {
        Dialog(onDismissRequest = { onTimePickerToggle(false) }) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TimePicker(state = timePickerState, modifier = Modifier.padding(bottom = 16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { onTimePickerToggle(false) }) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            onTimeSelected(timePickerState.hour, timePickerState.minute)
                            onTimePickerToggle(false)
                        }
                    ) {
                        Text("Accept")
                    }
                }
            }
        }
    }
}

@Composable
fun PrioritySelector(
    selectedPriority: TaskPriority,
    onPriorityChange: (TaskPriority) -> Unit
) {
    val priorities = TaskPriority.entries

    val selectedIndex = priorities.indexOf(selectedPriority)

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Select Priority",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            priorities.forEachIndexed { index, taskPriority ->
                SegmentedButton(
                    selected = index == selectedIndex,
                    onClick = { onPriorityChange(taskPriority) },
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = priorities.size),
                    label = { Text(taskPriority.label) },
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = TextColor,
                        activeContentColor = Color.White,
                        inactiveContentColor = TextColor,
                        inactiveContainerColor = Color.White,

                    )
                )
            }
        }
    }
}
