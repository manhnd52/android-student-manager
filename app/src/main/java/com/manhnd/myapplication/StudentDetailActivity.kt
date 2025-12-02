package com.manhnd.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class StudentDetailActivity : AppCompatActivity() {

    private lateinit var idInput: EditText
    private lateinit var nameInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var addressInput: EditText

    private var studentIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_student_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detail_root)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupViews()
        loadStudentData()
        setupActions()
    }

    private fun setupViews() {
        idInput = findViewById(R.id.input_student_id)
        nameInput = findViewById(R.id.input_student_name)
        phoneInput = findViewById(R.id.input_student_phone)
        addressInput = findViewById(R.id.input_student_address)
    }

    private fun loadStudentData() {
        studentIndex = intent.getIntExtra(EXTRA_STUDENT_INDEX, -1)
        val student = StudentRepository.getStudents().getOrNull(studentIndex)
        if (student == null) {
            Toast.makeText(this, "Không tìm thấy sinh viên", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        idInput.setText(student.id)
        nameInput.setText(student.name)
        phoneInput.setText(student.phone)
        addressInput.setText(student.address)
    }

    private fun setupActions() {
        val updateButton: Button = findViewById(R.id.button_update)
        updateButton.setOnClickListener {
            if (studentIndex !in StudentRepository.getStudents().indices) {
                Toast.makeText(this, "Không thể cập nhật sinh viên", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val id = idInput.text.toString().trim()
            val name = nameInput.text.toString().trim()
            val phone = phoneInput.text.toString().trim()
            val address = addressInput.text.toString().trim()

            if (id.isEmpty() || name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            StudentRepository.updateStudent(studentIndex, Student(id, name, phone, address))
            Toast.makeText(this, "Đã cập nhật thông tin", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    companion object {
        const val EXTRA_STUDENT_INDEX = "extra_student_index"
    }
}
