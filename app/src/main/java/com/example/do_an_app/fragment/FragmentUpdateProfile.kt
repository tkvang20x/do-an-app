package com.example.do_an_app.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.do_an_app.R
import com.example.do_an_app.databinding.FragmentUpdateProfileBinding
import com.example.do_an_app.viewmodel.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.do_an_app.fragment.FragmentHome.Companion.data_user
import com.example.do_an_app.model.users.Data
import com.example.do_an_app.model.users.DataUpdate

class FragmentUpdateProfile : Fragment() {
    lateinit var binding: FragmentUpdateProfileBinding
    private lateinit var userViewModel: UserViewModel
    var code = ""
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

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnUpdate.setOnClickListener {
            val userUpdate = DataUpdate(
                binding.txtCourse.text.toString(),
                binding.txtBirthday.text.toString(),
                binding.txtEmail.text.toString(),
                binding.txtGender.text.toString(),
                binding.txtFullName.text.toString(),
                binding.txtPhone.text.toString(),
                binding.txtUniversity.text.toString()
            )

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Sửa Thông Tin")
            builder.setMessage("Bạn Có Muốn Xác Nhận Sửa?")
            builder.setNegativeButton(("No"), { dialogInterface: DialogInterface, i: Int -> dialogInterface.dismiss() })
            builder.setPositiveButton(("Yes"), { dialogInterface: DialogInterface, i:Int ->
                userViewModel.updateUser(data_user.code ,userUpdate)

                dialogInterface.dismiss()

                findNavController().navigate(R.id.action_fragmentUpdateProfile_to_fragmentProfile)
                val builder2 = AlertDialog.Builder(requireContext())
                builder2.setMessage("Sửa thành công!!!")
                builder2.show()
            })
            builder.show()

        }


        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }
}