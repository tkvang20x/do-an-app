package com.example.do_an_app.fragment.fragmentstatus

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.example.do_an_app.databinding.FragmentStatusWaitingBinding
import com.example.do_an_app.fragment.FragmentHome
import com.example.do_an_app.model.voucher.Result
import com.example.do_an_app.viewmodel.VoucherViewModel
import com.example.do_an_app.fragment.FragmentHome.Companion.code_user

class FragmentStatusWaiting: Fragment(), CallbackVoucher {
    private lateinit var binding: FragmentStatusWaitingBinding
        private lateinit var adapter: VoucherAdapter
        private val list1 = arrayListOf<Result>()
        private lateinit var voucherViewModel: VoucherViewModel
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatusWaitingBinding.inflate(inflater, container, false)
//        binding.loading.visibility = View.VISIBLE
        list1.clear()

        voucherViewModel = VoucherViewModel()
        voucherViewModel.getVoucherUserId(1, code_user, "WAITING_CONFIRM")
        voucherViewModel.dataVoucher.observe(viewLifecycleOwner) {
            if (it != null) {
                list1.addAll(it.data.result)
                adapter.notifyDataSetChanged()

            }
            // Ẩn progressBar khi kết thúc load dữ liệu
            binding.loading.visibility = View.GONE
        }

        adapter = VoucherAdapter(list1, this)
        binding.rvList1.adapter = adapter
        binding.rvList1.layoutManager =
            LinearLayoutManager(FragmentHome().context, LinearLayoutManager.VERTICAL, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onClick(voucher: Result) {
        val bundle = Bundle()
        bundle.putString("voucher_id", voucher.voucher_id)
        bundle.putString("message", "WAITING")
        findNavController().navigate(R.id.action_fragmentVoucher_to_fragmentViewVoucher, bundle)
    }

    override fun onLongClick(groups: Result) {
        return
    }
}