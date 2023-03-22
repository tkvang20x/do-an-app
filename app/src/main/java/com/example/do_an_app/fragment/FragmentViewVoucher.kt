package com.example.do_an_app.fragment

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
import com.example.do_an_app.adapter.BooksAdapter
import com.example.do_an_app.adapter.ItemBookAdapter
import com.example.do_an_app.callback.CallBackItemBook
import com.example.do_an_app.databinding.FragmentViewVoucherBinding
import com.example.do_an_app.model.books.Result
import com.example.do_an_app.model.voucher.BooksBorrowed
import com.example.do_an_app.viewmodel.UserViewModel
import com.example.do_an_app.viewmodel.VoucherViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class FragmentViewVoucher: Fragment(), CallBackItemBook {
    private lateinit var binding: FragmentViewVoucherBinding
    private lateinit var voucherViewModel: VoucherViewModel
    private var voucher_id = ""
    private val list = arrayListOf<BooksBorrowed>()
    private lateinit var adapter :ItemBookAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewVoucherBinding.inflate(inflater, container, false)
        binding.loading.visibility = View.VISIBLE

        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bnv_view)
        view.visibility = View.GONE
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        voucher_id = arguments?.getString("voucher_id").toString()

        adapter = ItemBookAdapter(list,this)
        binding.rvListBook.adapter = adapter
        binding.rvListBook.layoutManager =
            LinearLayoutManager(FragmentHome().context, LinearLayoutManager.VERTICAL, false)

        voucherViewModel = VoucherViewModel()
        voucherViewModel.getDetailVoucher(voucher_id)
        voucherViewModel.dataDetailVoucher.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.voucher = it.data

                list.clear()
                list.addAll(it.data.books_borrowed)
                adapter.notifyDataSetChanged()
            }
            // Ẩn progressBar khi kết thúc load dữ liệu
            binding.loading.visibility = View.GONE
        }



        return binding.root
    }

    override fun onClick(books: BooksBorrowed) {
        TODO("Not yet implemented")
    }

    override fun onLongClick(job: BooksBorrowed) {
        TODO("Not yet implemented")
    }
}