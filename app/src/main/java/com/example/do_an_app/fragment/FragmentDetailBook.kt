package com.example.do_an_app.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.do_an_app.R
import com.example.do_an_app.databinding.FragmentDetailBookViewBinding
import com.example.do_an_app.viewmodel.BookViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.do_an_app.fragment.FragmentCreateVoucher.Companion.list_book
import com.example.do_an_app.model.book.Data
import com.example.do_an_app.model.voucher.ListIdBook

class FragmentDetailBook: Fragment() {
    private lateinit var binding: FragmentDetailBookViewBinding
    private var code_id = ""
    private lateinit var bookViewModel: BookViewModel
    private var message =""
    private var index = 0
    private lateinit var dataBook: Data

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBookViewBinding.inflate(inflater, container, false)
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bnv_view)
        view.visibility = View.GONE
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
        code_id = arguments?.getString("code_id").toString()
        index = arguments?.getInt("index")!!
        message = arguments?.getString("message").toString()

        Log.d("messagesaaaaaaaaaaaaaaa", message.toString())

        if (message == "view_voucher"){
            binding.btnRemoveBook.visibility = View.GONE
            binding.btnAddBook.visibility = View.GONE
        }else if (message == "create_voucher"){
            binding.btnRemoveBook.visibility = View.VISIBLE
            binding.btnAddBook.visibility = View.GONE
        }else if(message == "scan_qr"){
            binding.btnAddBook.visibility = View.VISIBLE
            binding.btnRemoveBook.visibility = View.GONE
        }

        bookViewModel = BookViewModel()
        bookViewModel.getDetailBook(code_id)
        bookViewModel.detailBook.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.book = it.data
                dataBook = it.data
            }
        }

       binding.btnAddBook.setOnClickListener {
           val addDataBook = ListIdBook(dataBook.code_id,dataBook.books.name,dataBook.books.author,dataBook.books.avatar)

            list_book.add(addDataBook)
           findNavController().navigate(R.id.action_fragmentDetailBook_to_fragmentCreateVoucher)
       }


        binding.btnRemoveBook.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Xóa sách khỏi phiếu mượn")
            builder.setMessage("Bạn có muốn xác nhận xóa?")
            builder.setNegativeButton(("No")) { dialogInterface: DialogInterface, i: Int -> dialogInterface.dismiss() }
            builder.setPositiveButton(("Yes")) { dialogInterface: DialogInterface, i: Int ->
                list_book.removeAt(index)
                dialogInterface.dismiss()

                findNavController().navigate(R.id.action_fragmentDetailBook_to_fragmentCreateVoucher)
                val builder2 = AlertDialog.Builder(requireContext())
                builder2.setMessage("Xóa thành công!!!")
                builder2.show()
            }
            builder.show()
        }

        return binding.root
    }
}