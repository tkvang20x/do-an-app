package com.example.do_an_app.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.do_an_app.databinding.FragmentRegisterBinding
import com.example.do_an_app.model.register.DataRegister
import com.example.do_an_app.viewmodel.RegisterViewModel
import java.text.SimpleDateFormat
import java.util.*

class FragmentRegister: Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    lateinit var model: RegisterViewModel
    private val myCalendar = Calendar.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container,false )
        model = ViewModelProvider(requireActivity())[RegisterViewModel::class.java]



        val date =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabel()
            }

        binding.txtBirthDay.setOnClickListener {
                DatePickerDialog(
                    this.requireContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
        }


        binding.btnRegister.setOnClickListener {
            val mDataRegister = DataRegister(
                "",
                binding.txtCourse.text.toString(),
                binding.txtBirthDay.text.toString(),
                binding.txtEmail.text.toString(),
                binding.txtGender.text.toString(),
                binding.txtFullName.text.toString(),
                binding.txtPassword.text.toString(),
                binding.txtPhone.text.toString(),
               "USER",
                binding.txtUniversity.text.toString(),
                binding.txtUserName.text.toString(),

            )

            model.postRegister(mDataRegister)
            model.dataRegister.observe(viewLifecycleOwner) {
//                Log.d("xxxxxxxxxxxxxxxxxxxx", it.toString())
                if (it?.status == 201) {
                    Toast.makeText(this.context, "Đăng ký thành công !!!", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().popBackStack()
                }
            }
        }


        binding.tvLogin.setOnClickListener {
            val action = FragmentRegisterDirections.actionFragmentRegisterToFragmentLogin()
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun updateLabel() {
        val myFormat = "MM/dd/yyyy" // định dạng ngày
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.txtBirthDay.setText(sdf.format(myCalendar.time))
    }
}