package com.manhnd.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var studentIdInput: EditText
    private lateinit var studentNameInput: EditText
    private lateinit var addButton: Button
    private lateinit var updateButton: Button
    private lateinit var studentRecyclerView: RecyclerView

    private val students = mutableListOf<Student>()
    private var selectedIndex: Int? = null
    private lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupViews()
        setupRecyclerView()
        setupActions()
    }

    private fun setupViews() {
        studentIdInput = findViewById(R.id.input_student_id)
        studentNameInput = findViewById(R.id.input_student_name)
        addButton = findViewById(R.id.button_add)
        updateButton = findViewById(R.id.button_update)
        studentRecyclerView = findViewById(R.id.student_list)
    }

    private fun setupRecyclerView() {
        adapter = StudentAdapter(
            students,
            onItemClicked = { student, position ->
                selectedIndex = position
                studentIdInput.setText(student.id)
                studentNameInput.setText(student.name)
            },
            onDeleteClicked = { position ->
                removeStudent(position)
            }
        )
        studentRecyclerView.layoutManager = LinearLayoutManager(this)
        studentRecyclerView.adapter = adapter
    }

    private fun setupActions() {
        addButton.setOnClickListener {
            val id = studentIdInput.text.toString().trim()
            val name = studentNameInput.text.toString().trim()
            if (id.isEmpty() || name.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            students.add(Student(id, name))
            adapter.notifyItemInserted(students.lastIndex)
            clearSelection()
        }

        updateButton.setOnClickListener {
            val index = selectedIndex
            val id = studentIdInput.text.toString().trim()
            val name = studentNameInput.text.toString().trim()
            if (id.isEmpty() || name.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (index != null && index in students.indices) {
                students[index] = students[index].copy(id = id, name = name)
                adapter.notifyItemChanged(index)
                clearSelection()
            } else {
                Toast.makeText(this, "Hãy chọn sinh viên để cập nhật", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun removeStudent(position: Int) {
        if (position !in students.indices) return
        students.removeAt(position)
        adapter.notifyItemRemoved(position)
        selectedIndex = when {
            selectedIndex == null -> null
            selectedIndex == position -> null
            selectedIndex!! > position -> selectedIndex!! - 1
            else -> selectedIndex
        }
        if (selectedIndex == null) clearInputFields()
    }

    private fun clearSelection() {
        selectedIndex = null
        clearInputFields()
    }

    private fun clearInputFields() {
        studentIdInput.text.clear()
        studentNameInput.text.clear()
    }
}

data class Student(
    val id: String,
    val name: String,
)

class StudentAdapter(
    private val students: List<Student>,
    private val onItemClicked: (Student, Int) -> Unit,
    private val onDeleteClicked: (Int) -> Unit,
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): StudentViewHolder {
        val view = layoutInflater(parent).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.bind(student)
        holder.itemView.setOnClickListener { onItemClicked(student, holder.bindingAdapterPosition) }
        holder.deleteButton.setOnClickListener {
            val adapterPosition = holder.bindingAdapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) {
                onDeleteClicked(adapterPosition)
            }
        }
    }

    private fun layoutInflater(parent: android.view.ViewGroup) = android.view.LayoutInflater.from(parent.context)

    class StudentViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        private val nameText: android.widget.TextView = itemView.findViewById(R.id.text_student_name)
        private val idText: android.widget.TextView = itemView.findViewById(R.id.text_student_id)
        val deleteButton: android.widget.ImageButton = itemView.findViewById(R.id.button_delete)

        fun bind(student: Student) {
            nameText.text = student.name
            idText.text = student.id
        }
    }
}
