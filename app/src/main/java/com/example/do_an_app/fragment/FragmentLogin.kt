package com.example.do_an_app.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.btl_timviec.sharepreference.MySharedPreference
import com.example.do_an_app.Const
import com.example.do_an_app.MainActivity
import com.example.do_an_app.databinding.FragmentLoginBinding
import com.example.do_an_app.model.login.DataLogin
import com.example.do_an_app.viewmodel.LoginViewModel

class FragmentLogin : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    lateinit var model: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        model = ViewModelProvider(this)[LoginViewModel::class.java]
//
        binding.tvRegister.setOnClickListener {
            val action = FragmentLoginDirections.actionFragmentLoginToFragmentRegister()
            findNavController().navigate(action)
        }


        binding.btnLogin.setOnClickListener {
            val login =
                DataLogin(binding.txtUserName.text.toString(), binding.txtPassword.text.toString())
            if (binding.txtUserName.text.toString() != "" && binding.txtPassword.text.toString() != "") {

                model.postLogin(login)
                model.dataLogin.observe(viewLifecycleOwner) {
                    Log.d("loginnnnnnnnnnnnnnnnn", it.toString())
                    if (it?.error == "LOGIN FAIL- User name not exist!") {
                        Toast.makeText(this.context, "Tài khoản không tồn tại!", Toast.LENGTH_LONG)
                            .show()
                    } else if (it?.error == "LOGIN FAIL- PASSWORD INCORRECT !!!!") {
                        Toast.makeText(this.context, "Mật khẩu không chính xác!", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        it?.data?.let { it1 -> MySharedPreference(requireActivity()).putToken(it1.token) }

                        Const.TOKEN = MySharedPreference(requireActivity()).getToken().toString()

                        val intent = Intent()
                        intent.setClass(requireActivity(), MainActivity::class.java)
                        startActivity(intent)
                    }
                    Log.d("zz", "xx${it?.toString()}")
                }
            } else {
                Toast.makeText(this.context, "Điền thiếu thông tin", Toast.LENGTH_LONG)
                    .show()
                Log.d("zz", "xx${login}")
            }
        }

        binding.tvForget.setOnClickListener {
            val action = FragmentLoginDirections.actionFragmentLoginToFragmentForgetPassword()
            findNavController().navigate(action)
        }


        return binding.root
    }
}