package com.example.do_an_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.do_an_app.R
import com.example.do_an_app.databinding.FragmentDetailBooksBinding
import com.example.do_an_app.viewmodel.BooksViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class FragmentDetailBooks: Fragment() {
    private lateinit var binding: FragmentDetailBooksBinding
    private var code = ""
    private lateinit var booksViewModel: BooksViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBooksBinding.inflate(layoutInflater, container, false)

        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bnv_view)
        view.visibility = View.GONE
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        code = arguments?.getString("code").toString()

        booksViewModel = ViewModelProvider(requireActivity()).get(BooksViewModel::class.java)
        booksViewModel.getDetailBooks(code)
        booksViewModel.detailBooks.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.books = it.data
            }
        }



        return binding.root
    }
}