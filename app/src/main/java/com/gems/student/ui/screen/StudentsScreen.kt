package com.gems.student.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gems.student.database.AppDatabase
import com.gems.student.database.model.Course
import com.gems.student.database.model.Enrollment
import com.gems.student.navigation.NavigationItem
import com.gems.student.ui.theme.StudentTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun StudentsScreen(navController: NavHostController) {
    val courseList = remember { mutableStateOf(listOf<Course>()) }
    val showMenu = remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }
    val context = navController.context
    val database = remember { AppDatabase.getInstance(context) }
    val courseDao = database.courseDao()
    val enrollmentDao = database.enrollmentDao()
    val studentId =
        navController.currentBackStackEntry?.arguments?.getString("studentId")?.toIntOrNull() ?: -1

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {

                                IconButton(onClick = { showMenu.value = !showMenu.value }) {
                                    Icon(
                                        imageVector = Icons.Default.AccountCircle,
                                        contentDescription = null,
                                        tint = Color.Gray,
                                        modifier = Modifier.size(35.dp)
                                    )
                                }
                                DropdownMenu(
                                    expanded = showMenu.value,
                                    onDismissRequest = { showMenu.value = false }
                                ) {
                                    DropdownMenuItem(
                                        onClick = { /* TODO: Set Image */ },
                                        text = { Text("Set image") },
                                        leadingIcon = {
                                            Icon(
                                                imageVector = Icons.Default.Face,
                                                contentDescription = null
                                            )
                                        }
                                    )
                                    DropdownMenuItem(
                                        onClick = { navController.navigate(NavigationItem.RegScreen.route) },
                                        text = { Text("Log out") },
                                        leadingIcon = {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Default.ExitToApp,
                                                contentDescription = null
                                            )
                                        }
                                    )
                                }
                                Text("Courses", fontWeight = FontWeight.Bold, fontSize = 29.sp)
                            }
                            IconButton(
                                onClick = { showDialog.value = true },
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(color = Color(0xFFF0EEFC))
                                    .size(40.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "Add"
                                )
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 10.dp, 0.dp)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(10.dp)
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                courseList.value.forEach { course ->
                    CourseCard(course = course)
                }
            }

            val courseName = remember { mutableStateOf("") }
            val description = remember { mutableStateOf("") }
            val duration = remember { mutableStateOf("") }

            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    title = { Text("Add a Course") },
                    text = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            OutlinedTextField(
                                value = courseName.value,
                                onValueChange = { courseName.value = it },
                                label = { Text("Course Name") }
                            )
                            Spacer(modifier = Modifier.padding(5.dp))
                            OutlinedTextField(
                                value = description.value,
                                onValueChange = { description.value = it },
                                label = { Text("Description") },
                                modifier = Modifier.height(100.dp)
                            )
                            Spacer(modifier = Modifier.padding(5.dp))
                            OutlinedTextField(
                                value = duration.value,
                                onValueChange = { duration.value = it },
                                label = { Text("Duration (in weeks)") }
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                val lastId = courseDao.getLastCourseId() ?: 0
                                val newId = lastId + 1

                                val lastId2 = enrollmentDao.getLastEnrollmentId() ?: 0
                                val new = lastId2 + 1


                                val newCourse = Course(
                                    id = newId,
                                    courseName = courseName.value,
                                    description = description.value,
                                    duration = duration.value.toIntOrNull() ?: 0
                                )

                                val currentDateTime = LocalDateTime.now()
                                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                val formattedDateTime = currentDateTime.format(formatter)

                                val enrollment = Enrollment(
                                    id = new,
                                    studentId = studentId,
                                    courseId = newId,
                                    enrollmentDate = formattedDateTime
                                )
                                if (courseName.value.isNotBlank() && description.value.isNotBlank() && duration.value.isNotBlank()) {
                                    courseDao.addCourse(newCourse)
                                    database.enrollmentDao().addEnrollment(enrollment)
                                    courseList.value = courseList.value + newCourse
                                    showDialog.value = false
                                }

                            }
                        ) {
                            Text("Add")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun CourseCard(course: Course) {
    Card(
        modifier = Modifier
            .size(185.dp, 130.dp)
            .padding(5.dp)
    ) {
        Column(modifier = Modifier.padding(5.dp)) {
            Text(
                "Course Name: ${course.courseName}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text("Description: ${course.description}", maxLines = 2, fontSize = 14.sp)
            Text("Duration: ${course.duration} weeks", fontSize = 14.sp)
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    StudentTheme {
        StudentsScreen(rememberNavController())
    }
}