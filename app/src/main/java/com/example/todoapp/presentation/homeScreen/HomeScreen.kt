package com.example.todoapp.presentation.homeScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.R
import com.example.todoapp.presentation.common.GradiantBackgroundBox
import com.example.todoapp.presentation.homeScreen.component.TaskItem
import com.example.todoapp.presentation.homeScreen.component.getPriorityColors
import com.example.todoapp.presentation.homeScreen.component.states.TaskItemEvents
import com.example.todoapp.presentation.homeScreen.component.states.TaskItemState
import com.example.todoapp.presentation.homeScreen.component.viewModel.HomeViewModel
import com.example.todoapp.presentation.homeScreen.component.viewModel.HomeViewModelState
import com.example.todoapp.ui.theme.MediumTurquoise
import com.example.todoapp.ui.theme.Poppins
import com.example.todoapp.ui.theme.TextColor

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.homeUiState.collectAsState()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("create_task_screen") },
                containerColor = MediumTurquoise,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) { paddingValues ->

        HomeScreenContent(
            modifier.padding(paddingValues),
            uiState = uiState,
            onSegmentSelected = viewModel::onSegmentSelected,
            onDeleteTask = viewModel::onDeleteTask,
            onDoneTask = viewModel::onDoneTask
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun HomeScreenContent(
    modifier: Modifier,
    uiState: HomeViewModelState,
    onSegmentSelected: (Int) -> Unit,
    onDeleteTask: (Int) -> Unit,
    onDoneTask: (Int) -> Unit
) {
    GradiantBackgroundBox(
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 21.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopSection(
                modifier = Modifier.padding(top = 20.dp),

            )

            Spacer(Modifier.height(10.dp))
            TaskSection(
                uiState = uiState,
                onSegmentSelected = onSegmentSelected,
                onDeleteTask = onDeleteTask,
                onDoneTask = onDoneTask
            )
        }
    }
}

@Composable
fun TopSection(
    modifier: Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.avatar_0),
            contentDescription = "Logo",
            modifier = Modifier
                .height(150.dp)
                .width(150.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(30.dp))
            Text(
                modifier = Modifier.weight(1f),
                text = "Welcome Alperen",
                fontSize = 28.sp,
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                color = TextColor,
                textAlign = TextAlign.Center
            )

            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Edit",
                    modifier = Modifier.size(30.dp)

                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskSection(
    uiState: HomeViewModelState,
    onSegmentSelected: (Int) -> Unit,
    onDeleteTask: (Int) -> Unit,
    onDoneTask: (Int) -> Unit,

) {
    val selectedIndex = uiState.selectedIndex
    val tasks = uiState.tasks
    val option = listOf("Not Done", "Done")

    val tasksToShow = if (selectedIndex == 0) {
        tasks.filter { !it.isDone }
    } else {
        tasks.filter { it.isDone }
    }

    val customColor = SegmentedButtonDefaults.colors(
        activeContainerColor = TextColor,
        activeContentColor = Color.White,

        inactiveContentColor = TextColor,
        inactiveContainerColor = Color.White,

    )

    SingleChoiceSegmentedButtonRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        option.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(index = index, count = option.size),
                onClick = {
                    onSegmentSelected(index)
                },
                selected = index == selectedIndex,
                colors = customColor,
                icon = { SegmentedButtonDefaults.Icon(false) },
                border = SegmentedButtonDefaults.borderStroke(color = Color.Transparent)
            ) {
                Text(label)
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        items(tasksToShow, key = { it.id }) { task ->
            val (textColor, backgroundColor) = getPriorityColors(task.priority)

            TaskItem(
                state = TaskItemState(
                    taskTitle = task.title,
                    priorityText = task.priority.label,
                    description = task.description,
                    dueDate = task.dueDate,
                    priorityTextColor = textColor,
                    priorityBackgroundColor = backgroundColor,
                    isTaskDone = task.isDone

                ),
                events = TaskItemEvents(
                    onDoneClick = { onDoneTask(task.id) },
                    onDeleteClick = { onDeleteTask(task.id) }
                ),

            )
            Spacer(Modifier.height(20.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}
