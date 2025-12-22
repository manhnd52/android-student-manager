package com.manhnd.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StudentViewModel : ViewModel() {
    private val _students = MutableLiveData<List<Student>>(emptyList())
    val students: LiveData<List<Student>> = _students

    private var currentId = 1

    fun addStudent(name: String, mssv: String) {
        val newStudent = Student(currentId++, name, mssv)
        _students.value = _students.value.orEmpty() + newStudent
    }

    fun updateStudent(id: Int, name: String, mssv: String) {
        _students.value = _students.value.orEmpty().map {
            if (it.id == id) it.copy(name = name, mssv = mssv) else it
        }
    }

    fun removeStudent(id: Int) {
        _students.value = _students.value.orEmpty().filter { it.id != id }
    }

    fun getStudentById(id: Int): Student? {
        return _students.value.orEmpty().firstOrNull { it.id == id }
    }
}
