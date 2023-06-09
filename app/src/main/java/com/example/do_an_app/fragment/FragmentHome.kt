package com.example.do_an_app.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.do_an_app.Const
import com.example.do_an_app.R
import com.example.do_an_app.adapter.Books2Adapter
import com.example.do_an_app.adapter.BooksAdapter
import com.example.do_an_app.adapter.ImageSliderAdapter
import com.example.do_an_app.callback.CallBack
import com.example.do_an_app.callback.CallBack2
import com.example.do_an_app.databinding.FragmentHomeBinding
import com.example.do_an_app.viewmodel.BooksViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.do_an_app.model.books.Result
import com.example.do_an_app.model.chart.BooksCount
import com.example.do_an_app.model.users.Data
import com.example.do_an_app.viewmodel.LoadingViewModel
import com.example.do_an_app.viewmodel.UserViewModel
import com.example.do_an_app.viewmodel.VoucherViewModel
import com.smarteist.autoimageslider.SliderView

class FragmentHome : Fragment(), CallBack, CallBack2 {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: BooksAdapter
    private lateinit var adapter2: Books2Adapter
    private lateinit var booksViewModel: BooksViewModel
    private lateinit var chartViewModel: VoucherViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var loadingViewModel: LoadingViewModel
    private val list = arrayListOf<Result>()
    private val list2 = arrayListOf<BooksCount>()
    private lateinit var adapter_image: ImageSliderAdapter

    companion object {
        var data_user: Data =
            Data(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                true,
                false,
                "",
                "",
                "",
                "",
                "",
                "USER",
                "",
                "",
                "",
                ""
            )

        var code_user = ""
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.loading1.visibility =  View.VISIBLE
        loadingViewModel = ViewModelProvider(requireActivity()).get(LoadingViewModel::class.java)

        loadingViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.loading1.visibility = if (isLoading) View.VISIBLE else View.GONE
        })



        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bnv_view)
        view.visibility = View.VISIBLE

        adapter_image = ImageSliderAdapter(Const.list_image)
        binding.slider.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        binding.slider.setSliderAdapter(adapter_image)
        binding.slider.scrollTimeInSec = 3
        binding.slider.isAutoCycle = true
        binding.slider.startAutoCycle()

        booksViewModel = BooksViewModel()
        booksViewModel.getBooks(1, 10, "", "", "")
        booksViewModel.dataBooks.observe(viewLifecycleOwner) {
            if (it != null) {
                list.clear()
                list.addAll(it.data.result)
                adapter.notifyDataSetChanged()

            }
            // Ẩn progressBar khi kết thúc load dữ liệu

        }

        adapter = BooksAdapter(list, this)
        binding.rvList.adapter = adapter
        binding.rvList.layoutManager =
            LinearLayoutManager(FragmentHome().context, LinearLayoutManager.HORIZONTAL, false)

        chartViewModel = VoucherViewModel()
        chartViewModel.getChart("05", "2023")
        chartViewModel.chart.observe(viewLifecycleOwner){
            if (it != null) {
                list2.clear()
                if (it.data.list_books_count.size > 10){
                    for (i in 0..9){
                        val books = BooksCount(it.data.list_books_count[i].author,
                                                it.data.list_books_count[i].code_books,
                                                it.data.list_books_count[i].count,
                            it.data.list_books_count[i].name,
                            it.data.list_books_count[i].avatar
                            )
                        list2.add(books)
                    }
                }else if (it.data.list_books_count.size in 1..9){
                    for (i in 0..it.data.list_books_count.size -1){
                        val books = BooksCount(it.data.list_books_count[i].author,
                            it.data.list_books_count[i].code_books,
                            it.data.list_books_count[i].count,
                            it.data.list_books_count[i].name,
                            it.data.list_books_count[i].avatar
                        )
                        list2.add(books)
                    }
                }
                adapter2.notifyDataSetChanged()
            }
        }

        adapter2 = Books2Adapter(list2, this)
        binding.rvList2.adapter = adapter2
        binding.rvList2.layoutManager =
            LinearLayoutManager(FragmentHome().context, LinearLayoutManager.HORIZONTAL, false)


        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        userViewModel.getUser()
        userViewModel.dataUser.observe(viewLifecycleOwner) {

            if (it == null) {
                binding.imgAvtUser.setImageResource(R.mipmap.ic_launcher)
            } else {
                data_user = it.data
                code_user = it.data.code.toString()
                if (it.data.avatar != null) {
                    Glide.with(binding.imgAvtUser)
                        .load(Const.BASE_URL + it.data.avatar.replace("\\", "/"))
                        .placeholder(R.mipmap.ic_launcher_round)
                        .error(R.mipmap.ic_launcher)
                        .into(binding.imgAvtUser)
                }
                binding.loading1.visibility = View.GONE
            }

        }

        binding.tvViewAll1.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHome_to_fragmentListBooks)
            list.clear()
        }


        binding.txtSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, actionId: Int, p2: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    val bundel = Bundle()
                    bundel.putString("name", binding.txtSearch.text.toString())
                    findNavController().navigate(R.id.action_fragmentHome_to_fragmentListBooks, bundel)
                    return true
                }
                return false
            }

        })

        return binding.root
    }

    override fun onClick(books: Result) {
        val bundle = Bundle()
        bundle.putString("code", books.code)
        findNavController().navigate(R.id.action_fragmentHome_to_fragmentDetailBooks, bundle)
        list.clear()
    }

    override fun onClick(books: BooksCount) {
        val bundle = Bundle()
        bundle.putString("code", books.code_books)
        findNavController().navigate(R.id.action_fragmentHome_to_fragmentDetailBooks, bundle)
        list.clear()
    }

    override fun onLongClick(job: BooksCount) {
        TODO("Not yet implemented")
    }



}

