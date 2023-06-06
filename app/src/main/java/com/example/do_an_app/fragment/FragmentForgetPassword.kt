package com.example.do_an_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.do_an_app.databinding.FragmentForgetPasswordBinding
import com.example.do_an_app.fragment.FragmentHome.Companion.data_user
import com.example.do_an_app.viewmodel.LoginViewModel

class FragmentForgetPassword: Fragment() {

    private lateinit var binding: FragmentForgetPasswordBinding
    private lateinit var loginViewModel : LoginViewModel
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgetPasswordBinding.inflate(inflater, container, false)

        binding.imgBack.setOnClickListener {
                val action = FragmentForgetPasswordDirections.actionFragmentForgetPasswordToFragmentLogin()
                findNavController().navigate(action)
        }

        loginViewModel = LoginViewModel()
        binding.btnLogin.setOnClickListener {
            val email = binding.txtUserName.text.toString().trim()
            val isValidEmail = email.matches(emailPattern.toRegex())
            if(isValidEmail){
                loginViewModel.forgotPassword(data_user.role, email)
                Toast.makeText(requireContext(), "Thay đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show()

                val action = FragmentForgetPasswordDirections.actionFragmentForgetPasswordToFragmentLogin()
                findNavController().navigate(action)
            }else{
                Toast.makeText(requireContext(), "Email không hợp lệ!", Toast.LENGTH_SHORT).show()
            }

        }

        return binding.root
    }
}