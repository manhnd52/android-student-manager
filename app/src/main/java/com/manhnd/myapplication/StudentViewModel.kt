package com.manhnd.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StudentViewModel : ViewModel() {
    private val _students = MutableLiveData<List<Student>>(emptyList())
    val students: LiveData<List<Student>> = _students

    private var currentId = 1

    fun addStudent(name: String, age: Int, major: String) {
        val newStudent = Student(currentId++, name, age, major)
        _students.value = _students.value.orEmpty() + newStudent
    }

    fun updateStudent(id: Int, name: String, age: Int, major: String) {
        _students.value = _students.value.orEmpty().map {
            if (it.id == id) it.copy(name = name, age = age, major = major) else it
        }
    }

    fun getStudentById(id: Int): Student? {
        return _students.value.orEmpty().firstOrNull { it.id == id }
    }
}
