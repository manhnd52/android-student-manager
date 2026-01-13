package com.manhnd.myapplication

import retrofit2.http.GET

interface StudentApi {
    @GET("students")
    suspend fun getStudents(): List<Student>
}
