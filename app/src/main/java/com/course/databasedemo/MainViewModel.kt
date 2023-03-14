package com.course.databasedemo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.course.databasedemo.data.Repository
import com.course.databasedemo.data.Student
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _firstNameState = MutableStateFlow("")
    val firstNameState = _firstNameState.asStateFlow()
    private val _lastNameState = MutableStateFlow("")
    val lastNameState = _lastNameState.asStateFlow()
    private val _markState = MutableStateFlow("")
    val markState = _markState.asStateFlow()
    private val _idState = MutableStateFlow("")
    val idState = _idState.asStateFlow()
    private val _students = MutableStateFlow(emptyList<Student>())
    val students = _students.asStateFlow()
    private val _student = MutableStateFlow<Student?>(null)
    private val _showStudents = MutableStateFlow(false)
    val showStudents = _showStudents.asStateFlow()
    private val _showAddResult = MutableStateFlow(false)
    val showAddResult = _showAddResult.asStateFlow()
    private val _showUpdateResult = MutableStateFlow(false)
    val showUpdateResult = _showUpdateResult.asStateFlow()
    private val _showDeleteResult = MutableStateFlow(false)
    val showDeleteResult = _showDeleteResult.asStateFlow()

    fun onEvent(event: Event) {
        when (event) {
            is Event.InputFirstName -> {
                _firstNameState.update { event.text }
            }
            is Event.InputId -> {
                _idState.update { event.text }
            }
            is Event.InputLastName -> {
                _lastNameState.update { event.text }
            }
            is Event.InputMark -> {
                _markState.update { event.text }
            }
        }
    }

    fun getAllStudents() {
        viewModelScope.launch {
            repository.getAllStudents().collectLatest { list ->
                Log.d("getall", "get all $list")
                _students.value = list
            }
        }
        _showStudents.update { true }
        _showAddResult.update { false }
        _showDeleteResult.update { false }
        _showUpdateResult.update { false }
    }

    private fun getStudent(id: String) {
        viewModelScope.launch {
            repository.getStudentById(id)?.collectLatest { student ->
                student?.let { item ->
                    _student.update { item }
                }
            }
        }
    }

    fun addStudent() {
        viewModelScope.launch {
            repository.insertStudent(
                Student().apply {
                    firstName = _firstNameState.value
                    lastName = _lastNameState.value
                    mark = _markState.value
                    id = _idState.value
                }
            )
        }
        _showStudents.update { false }
        _showAddResult.update { true }
        _showDeleteResult.update { false }
        _showUpdateResult.update { false }
    }

    fun deleteStudent(id: String) {
        getStudent(id)
        _student.value?.let {
            viewModelScope.launch {
                repository.deleteStudent(it._id)
            }
        }
        _showStudents.update { false }
        _showAddResult.update { false }
        _showDeleteResult.update { true }
        _showUpdateResult.update { false }
    }

    fun updateStudent(id: String) {
        getStudent(id)
        _student.value?.let {
            viewModelScope.launch {
                repository.updateStudent(
                    Student().apply {
                        firstName = _firstNameState.value
                        lastName = _lastNameState.value
                        mark = _markState.value
                        this.id = it.id
                        _id = it._id
                    }
                )
            }
        }
        _showStudents.update { false }
        _showAddResult.update { false }
        _showDeleteResult.update { false }
        _showUpdateResult.update { true }
    }
}
