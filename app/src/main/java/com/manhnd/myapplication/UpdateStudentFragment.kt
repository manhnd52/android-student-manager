package com.manhnd.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.manhnd.myapplication.databinding.FragmentUpdateStudentBinding

class UpdateStudentFragment : Fragment() {

    private var _binding: FragmentUpdateStudentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StudentViewModel by activityViewModels()
    private var studentId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        studentId = arguments?.getInt("studentId") ?: -1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateStudentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val student = viewModel.getStudentById(studentId)
        student?.let {
            binding.etName.setText(it.name)
            binding.etAge.setText(it.age.toString())
            binding.etMajor.setText(it.major)
        }

        binding.btnUpdate.setOnClickListener {
            val name = binding.etName.text.toString()
            val ageText = binding.etAge.text.toString()
            val major = binding.etMajor.text.toString()
            if (studentId != -1 && name.isNotBlank() && ageText.isNotBlank() && major.isNotBlank()) {
                val age = ageText.toIntOrNull() ?: return@setOnClickListener
                viewModel.updateStudent(studentId, name, age, major)
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
