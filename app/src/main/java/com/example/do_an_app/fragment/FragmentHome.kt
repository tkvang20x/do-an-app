package com.example.do_an_app.fragment

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.do_an_app.Const
import com.example.do_an_app.R
import com.example.do_an_app.adapter.BooksAdapter
import com.example.do_an_app.adapter.ImageSliderAdapter
import com.example.do_an_app.callback.CallBack
import com.example.do_an_app.databinding.FragmentHomeBinding
import com.example.do_an_app.model.Image
import com.example.do_an_app.viewmodel.BooksViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.do_an_app.model.books.Result
import com.smarteist.autoimageslider.SliderView

class FragmentHome : Fragment(), CallBack {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: BooksAdapter
    private lateinit var booksViewModel: BooksViewModel
    private val list = arrayListOf<Result>()
    private lateinit var adapter_image: ImageSliderAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.loading.visibility = View.VISIBLE

        adapter_image = ImageSliderAdapter(Const.list_image)
        binding.slider.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        binding.slider.setSliderAdapter(adapter_image)
        binding.slider.scrollTimeInSec = 3
        binding.slider.isAutoCycle = true
        binding.slider.startAutoCycle()

        adapter = BooksAdapter(list, this)
        binding.rvList.adapter = adapter
        binding.rvList.layoutManager =
            LinearLayoutManager(FragmentHome().context, LinearLayoutManager.HORIZONTAL, false)

        booksViewModel = ViewModelProvider(requireActivity()).get(BooksViewModel::class.java)
        booksViewModel.getBooks(1, 10, "", "")
        booksViewModel.dataBooks.observe(viewLifecycleOwner) {
            if (it != null) {
                list.clear()
                list.addAll(it.data.result)
                adapter.notifyDataSetChanged()

            }
            // Ẩn progressBar khi kết thúc load dữ liệu
            binding.loading.visibility = View.GONE
        }


        return binding.root
    }

    override fun onClick(books: Result) {
        TODO("Not yet implemented")
    }

    override fun onLongClick(job: Result) {
        TODO("Not yet implemented")
    }

}

