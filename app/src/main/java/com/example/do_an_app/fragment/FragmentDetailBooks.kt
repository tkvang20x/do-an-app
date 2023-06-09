package com.example.do_an_app.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.do_an_app.R
import com.example.do_an_app.databinding.FragmentDetailBooksBinding
import com.example.do_an_app.viewmodel.BooksViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.do_an_app.fragment.FragmentCreateVoucher.Companion.list_book
import kotlinx.coroutines.launch

class FragmentDetailBooks : Fragment() {
    private lateinit var binding: FragmentDetailBooksBinding
    private var code = ""
    private var amount = 1
    private lateinit var booksViewModel: BooksViewModel
    private var total_ready = 0

    override fun onPause() {
        super.onPause()
        view?.findViewById<RelativeLayout>(R.id.loading1)?.visibility = View.VISIBLE
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBooksBinding.inflate(layoutInflater, container, false)
        binding.loading.visibility = View.VISIBLE
        val view_bottom = requireActivity().findViewById<BottomNavigationView>(R.id.bnv_view)
        view_bottom.visibility = View.GONE
        binding.imgBack.setOnClickListener {
//            findNavController().popBackStack()
            findNavController().navigate(R.id.action_fragmentDetailBooks_to_fragmentHome)

        }
        code = arguments?.getString("code").toString()
        binding.edtAmount.setText(amount.toString())

        booksViewModel = BooksViewModel()
        booksViewModel.getDetailBooks(code)
        booksViewModel.detailBooks.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.books = it.data
                total_ready = it.data.total_ready
            }

            binding.loading.visibility = View.GONE
        }

        binding.imgPlus.setOnClickListener {

            if (amount == total_ready) {
                Toast.makeText(
                    requireContext(),
                    "Số lượng mượn vượt giới hạn có thể mượn!!!",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            amount += 1
            binding.edtAmount.setText(amount.toString())
        }
        binding.imgSub.setOnClickListener {
            if (amount <= 1) {
                return@setOnClickListener
            }
            amount -= 1
            binding.edtAmount.setText(amount.toString())
        }

        binding.btnAddBooks.setOnClickListener {
            if (amount > total_ready){
                Toast.makeText(
                    requireContext(),
                    "Sách mượn vượt quá giới hạn có thể mượn!!!",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            lifecycleScope.launch {
                booksViewModel.getListIdBook(code, amount, "READY")
                var apiResultReceived = false // biến theo dõi việc có kết quả trả về từ API
                booksViewModel.datalistIdBook.observe(viewLifecycleOwner) {
                    if (!apiResultReceived) { // chỉ thực hiện khi có kết quả trả về từ API
                        apiResultReceived = true
                        it?.data?.let { data ->
                            list_book.addAll(data)
                            Toast.makeText(
                                requireContext(),
                                "Thêm vào phiếu mượn thành công",
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().navigate(R.id.action_fragmentDetailBooks_to_fragmentHome)
                        }
                    }
                }
            }
        }


        return binding.root
    }

    fun Fragment.showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}