package com.course.databasedemo.data

import android.util.Log
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId

class RepositoryImpl(
    private val realm: Realm
) : Repository {
    override fun getStudent(studentId: ObjectId): Flow<Student?>? {
        return realm
            .query<Student>(query = "_id == $0", studentId)
            .first()
            .find()
            ?.asFlow()
            ?.map { it.obj }
    }

    override fun getStudentById(studentId: String): Flow<Student?>? {
        return realm
            .query<Student>(query = "id == $0", studentId)
            .first()
            .find()
            ?.asFlow()
            ?.map { it.obj }
    }

    override fun getAllStudents(): Flow<List<Student>> {
        return realm
            .query<Student>()
            .asFlow()
            .map { it.list }
    }

    override suspend fun insertStudent(student: Student) {
        realm.write {
            copyToRealm(student)
        }
    }

    override suspend fun updateStudent(student: Student) {
        realm.write {
            val queriedStudent = query<Student>(query = "_id == $0", student._id)
                .first()
                .find()
            queriedStudent?.apply {
                firstName = student.firstName
                lastName = student.lastName
                mark = student.mark
                id = student.id
            }
        }
    }

    override suspend fun deleteStudent(studentId: ObjectId) {
        realm.write {
            val queriedStudent = query<Student>(query = "_id == $0", studentId)
                .first()
                .find()
            try {
                queriedStudent?.let {
                    delete(it)
                }
            } catch (e: Exception) {
                Log.d("NoteDelete", "${e.message}")
            }
        }
    }
}
