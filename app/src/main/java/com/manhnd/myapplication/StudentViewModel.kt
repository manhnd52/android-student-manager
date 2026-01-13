package com.manhnd.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StudentViewModel : ViewModel() {
    private val _students = MutableLiveData<List<Student>>(emptyList())
    val students: LiveData<List<Student>> = _students

    private val api: StudentApi = Retrofit.Builder()
        .baseUrl("https://lebavui.io.vn/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(StudentApi::class.java)

    fun loadStudents() {
        viewModelScope.launch {
            runCatching { api.getStudents() }
                .onSuccess { _students.value = it }
                .onFailure { _students.value = emptyList() }
        }
    }

}
