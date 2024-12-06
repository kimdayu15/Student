package com.gems.student.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gems.student.database.AppDatabase
import com.gems.student.database.model.Student
import com.gems.student.ui.theme.StudentTheme

@Composable
fun RegScreen(navController: NavHostController) {
    val context = navController.context
    val database = remember { AppDatabase.getInstance(context) }
    val studentDao = database.studentDao()

    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }

    StudentTheme {
        Scaffold { inner ->

            Column(
                modifier = Modifier
                    .padding(inner)
                    .padding(20.dp)
            ) {
                Text("Register", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                OutlinedTextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = { Text("Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 12.dp, 10.dp, 0.dp),
                    placeholder = { Text("Enter your name") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = { Text("Email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp),
                    placeholder = { Text("Enter your email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)

                )
                OutlinedTextField(
                    value = phone.value,
                    onValueChange = { phone.value = it },
                    label = { Text("Phone") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp),
                    placeholder = { Text("Enter your phone") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )

                Button(
                    onClick = {
                        if (name.value.isNotBlank() && email.value.isNotBlank() && phone.value.isNotBlank()) {
                            val lastId = studentDao.getLastStudentId() ?: 0
                            val newId = lastId + 1

                            val student = Student(
                                id = newId,
                                name = name.value,
                                email = email.value,
                                phone = phone.value
                            )
                            studentDao.addStudent(student)

                            navController.navigate("student_screen/$newId")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text("Register")
                }
            }
        }


    }

}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun RegPreview() {
    RegScreen(rememberNavController())
}