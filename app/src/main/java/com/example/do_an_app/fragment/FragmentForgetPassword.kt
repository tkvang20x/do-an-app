package com.example.do_an_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.do_an_app.databinding.FragmentForgetPasswordBinding

class FragmentForgetPassword: Fragment() {

    private lateinit var binding: FragmentForgetPasswordBinding

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

        return binding.root
    }
}