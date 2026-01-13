package com.manhnd.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.manhnd.myapplication.databinding.FragmentStudentDetailBinding

class StudentDetailFragment : Fragment() {

    private var _binding: FragmentStudentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StudentViewModel by activityViewModels()
    private var studentId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        studentId = arguments?.getInt("studentId") ?: -1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.students.observe(viewLifecycleOwner) { students ->
            val student = students.firstOrNull { it.id == studentId }
            if (student != null) {
                binding.tvDetailName.text = getString(R.string.label_student_name, student.hoten)
                binding.tvDetailMssv.text = getString(R.string.label_student_mssv, student.mssv)
                binding.tvDetailBirth.text =
                    getString(R.string.label_student_birth, student.ngaysinh)
                binding.tvDetailEmail.text = getString(R.string.label_student_email, student.email)
                Glide.with(binding.ivDetailThumbnail)
                    .load(student.thumbnailUrl())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(binding.ivDetailThumbnail)
            }
        }

        if (viewModel.students.value.isNullOrEmpty()) {
            viewModel.loadStudents()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
