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
import com.example.do_an_app.R
import com.example.do_an_app.databinding.FragmentChangePassBinding
import com.example.do_an_app.model.login.DataPassword
import com.example.do_an_app.viewmodel.LoginViewModel

class FragmentChangePass: Fragment() {

    private lateinit var binding: FragmentChangePassBinding
    lateinit var model: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangePassBinding.inflate(inflater, container, false)

        model = ViewModelProvider(this)[LoginViewModel::class.java]

        binding.imgBack.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentChangePass_to_fragmentProfile)
        }

        binding.btnLogin.setOnClickListener {

            val changePass =
                DataPassword(binding.txtUserName.text.toString(), binding.txtPassword.text.toString())
            if (binding.txtUserName.text.toString() != "" && binding.txtPassword.text.toString() != "") {

                model.putPassword(changePass)
                model.dataPass.observe(viewLifecycleOwner) {
                    if (it?.data == false) {
                        Toast.makeText(this.context, "Mật khẩu cũ sai!", Toast.LENGTH_LONG)
                            .show()
//                        binding.csLoading.visibility = View.GONE
                    } else {
                        Toast.makeText(this.context, "Thay đổi mật khẩu thành công!", Toast.LENGTH_LONG)
                            .show()
                        findNavController().navigate(R.id.action_fragmentChangePass_to_fragmentProfile)
                    }
                    Log.d("zz", "xx${it?.toString()}")
                }
            } else {
                Toast.makeText(this.context, "Điền thiếu thông tin", Toast.LENGTH_LONG)
                    .show()
            }
        }

        return binding.root
    }
}