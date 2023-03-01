package com.example.do_an_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.do_an_app.databinding.FragmentRegisterBinding

class FragmentRegister: Fragment() {
    private lateinit var binding: FragmentRegisterBinding
//    lateinit var model: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container,false )
//        model = ViewModelProvider(requireActivity())[RegisterViewModel::class.java]
//
//
//        binding.btnRegister.setOnClickListener {
//            var mDataRegister = DataRegister(
//                binding.txtFullName.text.toString(),
//                binding.txtUserName.text.toString(),
//                binding.txtPassword.text.toString(),
//                binding.txtEmail.text.toString(),
//                binding.txtPhone.text.toString(),
//                binding.txtCareer.text.toString()
//            )
//
//            model.postRegister(mDataRegister)
//            model.dataRegister.observe(viewLifecycleOwner,{
//                if(it.message == "AUTH.REGISTER.SUCCESSFULLY"){
//                    Toast.makeText(this.context, "Đăng ký thành công !!!", Toast.LENGTH_SHORT).show()
//                    findNavController().popBackStack()
//                }
//            })
//        }


        binding.tvLogin.setOnClickListener {
            val action = FragmentRegisterDirections.actionFragmentRegisterToFragmentLogin()
            findNavController().navigate(action)
        }




        return binding.root
    }
}