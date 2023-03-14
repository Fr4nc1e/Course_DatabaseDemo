package com.course.databasedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.course.databasedemo.ui.theme.DatabaseDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DatabaseDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = hiltViewModel<MainViewModel>()

                    val firstName = viewModel.firstNameState.collectAsState().value
                    val lastName = viewModel.lastNameState.collectAsState().value
                    val mark = viewModel.markState.collectAsState().value
                    val id = viewModel.idState.collectAsState().value
                    val students = viewModel.students.collectAsState().value
                    val showStudents = viewModel.showStudents.collectAsState().value
                    val showUpdate = viewModel.showUpdateResult.collectAsState().value
                    val showDelete = viewModel.showDeleteResult.collectAsState().value
                    val showAdd = viewModel.showAddResult.collectAsState().value

                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "First Name: ")
                            TextField(
                                value = firstName,
                                onValueChange = {
                                    viewModel.onEvent(Event.InputFirstName(it))
                                }
                            )
                        }
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Last Name: ")
                            TextField(
                                value = lastName,
                                onValueChange = {
                                    viewModel.onEvent(Event.InputLastName(it))
                                }
                            )
                        }
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Mark: ")
                            TextField(
                                value = mark,
                                onValueChange = {
                                    viewModel.onEvent(Event.InputMark(it))
                                }
                            )
                        }
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Id: ")
                            TextField(
                                value = id,
                                onValueChange = {
                                    viewModel.onEvent(Event.InputId(it))
                                }
                            )
                        }
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Button(onClick = { viewModel.addStudent() }) {
                                Text(text = "Add Record")
                            }
                            Button(onClick = { viewModel.getAllStudents() }) {
                                Text(text = "View All Records")
                            }
                        }
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Button(onClick = { viewModel.updateStudent(id) }) {
                                Text(text = "Update A Record")
                            }
                            Button(onClick = { viewModel.deleteStudent(id) }) {
                                Text(text = "Delete A Record By Id")
                            }
                        }
                        when {
                            showDelete -> {
                                Text(text = "Delete Successfully")
                            }
                            showStudents -> {
                                LazyColumn {
                                    items(students) {
                                        Text(text = "Id: ${it.id}")
                                        Text(text = "First name: ${it.firstName}")
                                        Text(text = "Last name: ${it.lastName}")
                                        Text(text = "Mark: ${it.mark}")
                                    }
                                }
                            }
                            showAdd -> {
                                Text(text = "Add Successfully")
                            }
                            showUpdate -> {
                                Text(text = "Update Successfully")
                            }
                        }
                    }
                }
            }
        }
    }
}
