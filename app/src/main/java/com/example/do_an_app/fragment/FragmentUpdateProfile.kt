package com.example.do_an_app.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.do_an_app.R
import com.example.do_an_app.databinding.FragmentUpdateProfileBinding
import com.example.do_an_app.fragment.FragmentHome.Companion.data_user
import com.example.do_an_app.model.users.DataUpdate
import com.example.do_an_app.viewmodel.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.*

class FragmentUpdateProfile : Fragment() {
    lateinit var binding: FragmentUpdateProfileBinding
    private lateinit var userViewModel: UserViewModel
    var code = ""
    var genderUpdate = ""
    var genderView = ""
    var listGender = arrayListOf("Nam", "Nữ")
    private val myCalendar = Calendar.getInstance()
    private lateinit var dataUpdate: DataUpdate
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateProfileBinding.inflate(layoutInflater, container, false)

        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bnv_view)
        view.visibility = View.GONE

        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        binding.user = data_user

        if (data_user.role == "STUDENT") {
            binding.tvCourse.visibility = View.VISIBLE
            binding.tvCareerTitle.visibility = View.VISIBLE
            binding.txtCourse.visibility = View.VISIBLE
        } else {
            binding.tvDepartmentTitle.visibility = View.VISIBLE
            binding.tvDepartment.visibility = View.VISIBLE
            binding.txtDepartment.visibility = View.VISIBLE

            binding.tvSpecialTitle.visibility = View.VISIBLE
            binding.tvSpecial.visibility = View.VISIBLE
            binding.txtSpecial.visibility = View.VISIBLE
        }

        val genders = resources.getStringArray(R.array.Gender)
        // access the spinner
        val spinner = binding.spnGender
        val adapterSpinner = this.context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item, genders
            )
        }
        adapterSpinner?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapterSpinner

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?, position: Int, id: Long
            ) {
                genderUpdate = genders[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        if (data_user.gender.equals("MALE")) {
            genderView = "Nam"
        } else {
            genderView = "Nữ"
        }
        val index = listGender.indexOf(genderView)

        binding.spnGender.setSelection(index)

        val date =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabel()
            }

        binding.txtBirthday.setOnClickListener {
            DatePickerDialog(
                this.requireContext(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.imgBack.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentUpdateProfile_to_fragmentProfile)
        }

        binding.btnUpdate.setOnClickListener {

            if (binding.txtBirthday.text.toString().isEmpty() || binding.txtEmail.text.toString()
                    .isEmpty() || binding.txtFullName.text.toString()
                    .isEmpty() || binding.txtPhone.text.toString()
                    .isEmpty() || binding.txtUniversity.text.toString().isEmpty()
            ) {
                Toast.makeText(
                    requireContext(),
                    "Điền thiếu thông tin cần thiết!",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (binding.txtEmail.text.toString().length > 100 || binding.txtFullName.text.toString().length > 100 ||
                binding.txtPhone.text.toString().length > 10 || binding.txtUniversity.text.toString().length > 100
            ) {
                Toast.makeText(
                    requireContext(),
                    "Có thông tin vượt quá giới hạn ký tự cho phép!",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                var gender_user = ""

                if (genderUpdate.equals("Nam")) {
                    gender_user = "MALE"
                } else {
                    gender_user = "FEMALE"
                }

                if (data_user.role === "STUDENT") {
                    dataUpdate = DataUpdate(
                        binding.txtCourse.text.toString(),
                        binding.txtBirthday.text.toString(),
                        binding.txtEmail.text.toString(),
                        gender_user,
                        binding.txtFullName.text.toString(),
                        binding.txtPhone.text.toString(),
                        binding.txtUniversity.text.toString(),
                        "",
                        "",
                        data_user.role
                    )
                } else {
                    dataUpdate = DataUpdate(
                        binding.txtCourse.text.toString(),
                        binding.txtBirthday.text.toString(),
                        binding.txtEmail.text.toString(),
                        gender_user,
                        binding.txtFullName.text.toString(),
                        binding.txtPhone.text.toString(),
                        binding.txtUniversity.text.toString(),
                        binding.txtDepartment.text.toString(),
                        binding.txtSpecial.text.toString(),
                        data_user.role
                    )
                }


                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Sửa Thông Tin")
                builder.setMessage("Bạn Có Muốn Xác Nhận Sửa?")
                builder.setNegativeButton(
                    ("No"),
                    { dialogInterface: DialogInterface, i: Int -> dialogInterface.dismiss() })
                builder.setPositiveButton(("Yes"), { dialogInterface: DialogInterface, i: Int ->
                    userViewModel.updateUser(data_user.code, dataUpdate)

                    dialogInterface.dismiss()

                    findNavController().navigate(R.id.action_fragmentUpdateProfile_to_fragmentProfile)
                    val builder2 = AlertDialog.Builder(requireContext())
                    builder2.setMessage("Sửa thành công!!!")
                    builder2.show()
                })
                builder.show()
            }
        }


        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun updateLabel() {
        val myFormat = "yyyy/MM/dd" // định dạng ngày
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.txtBirthday.setText(sdf.format(myCalendar.time))
    }
}