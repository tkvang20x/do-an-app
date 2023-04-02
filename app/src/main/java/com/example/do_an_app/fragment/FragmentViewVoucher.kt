package com.example.do_an_app.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
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
    private var message = ""
    private val list = arrayListOf<BooksBorrowed>()
    private lateinit var adapter :ItemBookAdapter
    @SuppressLint("NotifyDataSetChanged")
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
        message = arguments?.getString("message").toString()

        if(message == "WAITING"){
            binding.btnCancelVoucher.visibility = View.VISIBLE
        }

        adapter = ItemBookAdapter(list,this)
        binding.rvListBook.adapter = adapter
        binding.rvListBook.layoutManager =
            LinearLayoutManager(FragmentHome().context, LinearLayoutManager.VERTICAL, false)

        voucherViewModel = VoucherViewModel()
        voucherViewModel.getDetailVoucher(voucher_id)
        voucherViewModel.dataDetailVoucher.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.voucher = it.data
                Log.d("abccccccccc", it.data.toString())
                list.clear()
                list.addAll(it.data.books_borrowed)

                binding.root.post {
                    adapter.notifyDataSetChanged()
                }
            }
            // Ẩn progressBar khi kết thúc load dữ liệu
            binding.loading.visibility = View.GONE
        }


        binding.btnCancelVoucher.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Hủy phiếu mượn")
            builder.setMessage("Bạn có muốn xác nhận hủy phiếu mượn?")
            builder.setNegativeButton(("No"), { dialogInterface: DialogInterface, i: Int -> dialogInterface.dismiss() })
            builder.setPositiveButton(("Yes")) { dialogInterface: DialogInterface, i: Int ->
                voucherViewModel.updateStatusVoucher(voucher_id, "CANCELLED")

                dialogInterface.dismiss()

                findNavController().navigate(R.id.action_fragmentViewVoucher_to_fragmentVoucher)
                val builder2 = AlertDialog.Builder(requireContext())
                builder2.setMessage("Hủy phiếu mượn thành công!!!")
                builder2.show()
            }
            builder.show()
        }


        return binding.root
    }

    override fun onClick(books: BooksBorrowed) {
        val bundle = Bundle()
        bundle.putString("code_id", books.code_id)
        bundle.putString("message", "view_voucher")
        findNavController().navigate(R.id.action_fragmentViewVoucher_to_fragmentDetailBook, bundle)
    }

    override fun onLongClick(job: BooksBorrowed) {
        TODO("Not yet implemented")
    }
}