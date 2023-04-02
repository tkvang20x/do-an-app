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
import com.example.do_an_app.callback.CallbackVoucher
import com.example.do_an_app.databinding.FragmentStatusCancelBinding
import com.example.do_an_app.fragment.FragmentHome
import com.example.do_an_app.model.voucher.Result
import com.example.do_an_app.viewmodel.VoucherViewModel

class FragmentStatusCancel: Fragment(), CallbackVoucher {
    private lateinit var binding:FragmentStatusCancelBinding
    private lateinit var adapter: VoucherAdapter
    private val list5 = arrayListOf<Result>()
    private lateinit var voucherViewModel: VoucherViewModel
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatusCancelBinding.inflate(inflater, container, false)
        list5.clear()
        binding.loading.visibility = View.VISIBLE

        voucherViewModel = VoucherViewModel()
        voucherViewModel.getVoucherUserId(1, FragmentHome.code_user, "CANCELLED")
        voucherViewModel.dataVoucher.observe(viewLifecycleOwner) {
            if (it != null) {
                list5.addAll(it.data.result)
                adapter.notifyDataSetChanged()
            }
            // Ẩn progressBar khi kết thúc load dữ liệu
            binding.loading.visibility = View.GONE
        }
        adapter = VoucherAdapter(list5, this)
        binding.rvList5.adapter = adapter
        binding.rvList5.layoutManager =
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