package com.course.databasedemo

sealed class Event {
    data class InputFirstName(val text: String) : Event()
    data class InputLastName(val text: String) : Event()
    data class InputMark(val text: String) : Event()
    data class InputId(val text: String) : Event()
}
