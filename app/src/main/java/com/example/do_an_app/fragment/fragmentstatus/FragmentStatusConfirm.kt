package com.example.do_an_app.fragment.fragmentstatus

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.do_an_app.R
import com.example.do_an_app.adapter.VoucherAdapter
import com.example.do_an_app.callback.CallBackGroups
import com.example.do_an_app.callback.CallbackVoucher
import com.example.do_an_app.databinding.FragmentStatusConfirmBinding
import com.example.do_an_app.fragment.FragmentHome
import com.example.do_an_app.model.voucher.Result
import com.example.do_an_app.viewmodel.VoucherViewModel
import com.google.android.play.core.internal.bi

class FragmentStatusConfirm: Fragment(), CallbackVoucher {
    private lateinit var binding: FragmentStatusConfirmBinding
    private lateinit var adapter: VoucherAdapter
    private val list4 = arrayListOf<Result>()
    private lateinit var voucherViewModel: VoucherViewModel
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatusConfirmBinding.inflate(inflater, container, false)
        list4.clear()
        binding.loading.visibility = View.VISIBLE

        voucherViewModel = VoucherViewModel()
        voucherViewModel.getVoucherUserId(1, FragmentHome.code_user, "CONFIRMED")
        voucherViewModel.dataVoucher.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.data.result.size == 0) {
                    binding.txtNullData.visibility = View.VISIBLE
                } else {
                    list4.addAll(it.data.result)
                    adapter.notifyDataSetChanged()
                }
            }
            binding.loading.visibility = View.GONE
        }

        adapter = VoucherAdapter(list4, this)
        binding.rvList2.adapter = adapter
        binding.rvList2.layoutManager =
            LinearLayoutManager(FragmentHome().context, LinearLayoutManager.VERTICAL, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onClick(voucher: Result) {
        val bundle = Bundle()
        bundle.putString("voucher_id", voucher.voucher_id)
        findNavController().navigate(R.id.action_fragmentVoucher_to_fragmentViewVoucher, bundle)
    }

    override fun onLongClick(groups: Result) {
        return
    }
}