package com.example.todoapp.presentation.homeScreen.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.R
import com.example.todoapp.presentation.homeScreen.component.states.TaskItemEvents
import com.example.todoapp.presentation.homeScreen.component.states.TaskItemState
import com.example.todoapp.ui.theme.DeleteButtonColor
import com.example.todoapp.ui.theme.DoneButtonColor
import com.example.todoapp.ui.theme.Poppins
import com.example.todoapp.ui.theme.TextColor
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    state: TaskItemState,
    events: TaskItemEvents

) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                TopInfoSection(
                    taskTitle = state.taskTitle,
                    priority = state.priorityText,
                    priorityTextColor = state.priorityTextColor,
                    priorityBackgroundColor = state.priorityBackgroundColor
                )
                Spacer(Modifier.height(10.dp))
                DateInfoSection(
                    date = state.dueDate
                )
                Spacer(Modifier.height(10.dp))
                ButtonSection(
                    isTaskDone = state.isTaskDone,
                    onDoneClick = { events.onDoneClick() },
                    onDeleteClick = { events.onDeleteClick() },
                )
                Spacer(Modifier.height(20.dp))
            }

            DescriptionSection(
                modifier = Modifier.padding(horizontal = 10.dp),
                isExpanded = isExpanded,
                onDescriptionClicked = {
                    isExpanded = !isExpanded
                },
                description = state.description,

            )

            Spacer(Modifier.height(10.dp))
        }
    }
}

@Composable
fun TopInfoSection(
    taskTitle: String,
    priority: String,
    priorityTextColor: Color,
    priorityBackgroundColor: Color

) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = taskTitle,
            color = TextColor,
            fontSize = 20.sp,
            fontFamily = Poppins,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            modifier = Modifier
                .clip(RoundedCornerShape(size = 8.dp))
                .background(priorityBackgroundColor)
                .padding(horizontal = 8.dp, vertical = 4.dp),
            text = priority,
            color = priorityTextColor,
            fontSize = 12.sp,
            fontFamily = Poppins,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateInfoSection(
    date: LocalDateTime
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Icon(
                imageVector = Icons.Default.CalendarToday,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(16.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = date.toLocalDate().toString(),
                color = Color.DarkGray,
                fontSize = 14.sp,
                fontFamily = Poppins,
                fontWeight = FontWeight.Normal
            )
        }

        Spacer(Modifier.width(16.dp))

        Row {
            Icon(
                imageVector = Icons.Default.AccessTime,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = date.toLocalTime().withNano(0).toString(),
                color = Color.DarkGray,
                fontSize = 14.sp,
                fontFamily = Poppins,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
fun ButtonSection(
    onDoneClick: () -> Unit,
    onDeleteClick: () -> Unit,
    isTaskDone: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!isTaskDone) {
            Button(
                onClick = { onDoneClick() },
                shape = RoundedCornerShape(size = 12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DoneButtonColor,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(8.dp))

                Text(
                    text = stringResource(R.string.done)
                )
            }
            Spacer(Modifier.width(10.dp))
        }

        Button(
            onClick = { onDeleteClick() },
            shape = RoundedCornerShape(size = 12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DeleteButtonColor,
                contentColor = Color.White
            ),
            modifier = Modifier
                .weight(1f)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(8.dp))

            Text(
                text = stringResource(R.string.delete)
            )
        }
    }
}

@Composable
fun DescriptionSection(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    onDescriptionClicked: () -> Unit,
    description: String
) {
    HorizontalDivider()
    Spacer(Modifier.height(10.dp))

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onDescriptionClicked() },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.description),
                color = TextColor,
                fontSize = 14.sp,
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.width(8.dp))

            Icon(
                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier.height(10.dp))

        if (isExpanded) {
            Text(
                text = description.ifBlank { stringResource(R.string.no_description) },
                color = TextColor,
                fontFamily = Poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
        }
    }
}
