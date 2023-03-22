package com.example.do_an_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.do_an_app.R
import com.example.do_an_app.databinding.FragmentCreateVoucherBinding

class FragmentCreateVoucher: Fragment() {

    private lateinit var binding: FragmentCreateVoucherBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateVoucherBinding.inflate(inflater, container, false)

        binding.tvViewVoucher.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentCreateVoucher_to_fragmentVoucher)
        }


        return binding.root
    }
}