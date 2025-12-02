package com.manhnd.myapplication

object StudentRepository {
    private val students = mutableListOf<Student>()

    fun getStudents(): MutableList<Student> = students

    fun addStudent(student: Student) {
        students.add(student)
    }

    fun updateStudent(index: Int, student: Student) {
        if (index in students.indices) {
            students[index] = student
        }
    }

    fun removeStudent(index: Int) {
        if (index in students.indices) {
            students.removeAt(index)
        }
    }
}
