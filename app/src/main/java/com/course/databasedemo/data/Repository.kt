package com.course.databasedemo.data

import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

interface Repository {
    fun getStudent(studentId: ObjectId): Flow<Student?>?
    fun getStudentById(studentId: String): Flow<Student?>?
    fun getAllStudents(): Flow<List<Student>>
    suspend fun insertStudent(student: Student)
    suspend fun updateStudent(student: Student)
    suspend fun deleteStudent(studentId: ObjectId)
}
