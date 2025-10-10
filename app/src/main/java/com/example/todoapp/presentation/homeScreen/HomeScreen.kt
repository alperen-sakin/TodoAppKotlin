package com.example.todoapp.presentation.homeScreen

import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.R
import com.example.todoapp.presentation.common.GradiantBackgroundBox
import com.example.todoapp.ui.theme.MediumTurquoise
import com.example.todoapp.ui.theme.Poppins
import com.example.todoapp.ui.theme.TextColor

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
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

        HomeScreenContent(modifier.padding(paddingValues))
    }
}

@Composable
private fun HomeScreenContent(modifier: Modifier) {
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
                modifier = modifier.padding(top = 50.dp)
            )

            Spacer(Modifier.height(10.dp))
            TaskSection()
        }
    }
}

@Composable
fun TopSection(
    modifier: Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.avatar_0),
        contentDescription = "Logo",
        modifier = modifier
            .height(200.dp)
            .width(200.dp)
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

@Composable
fun TaskSection() {
    var selectedIndex by rememberSaveable { mutableStateOf(0) }
    val option = listOf("Not Done", "Done")

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
                    selectedIndex = index
                    Log.d("TAG", "TaskSection: $label")
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
}

@Preview
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}
