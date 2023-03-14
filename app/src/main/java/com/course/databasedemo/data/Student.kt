package com.course.databasedemo.data

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Student : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId.invoke()
    var firstName: String = ""
    var lastName: String = ""
    var mark: String = ""
    var id: String = ""
}
