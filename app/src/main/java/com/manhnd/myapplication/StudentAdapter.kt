package com.manhnd.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private var students: List<Student>,
    private val onItemClick: (Student) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(students[position])
    }

    fun submitList(newStudents: List<Student>) {
        students = newStudents
        notifyDataSetChanged()
    }

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameText: TextView = itemView.findViewById(R.id.tvName)
        private val ageText: TextView = itemView.findViewById(R.id.tvAge)
        private val majorText: TextView = itemView.findViewById(R.id.tvMajor)

        fun bind(student: Student) {
            nameText.text = student.name
            ageText.text = itemView.context.getString(R.string.student_age_format, student.age)
            majorText.text = student.major
            itemView.setOnClickListener { onItemClick(student) }
        }
    }
}
