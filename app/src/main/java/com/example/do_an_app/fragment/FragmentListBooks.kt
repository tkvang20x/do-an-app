package com.example.do_an_app.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.do_an_app.R
import com.example.do_an_app.adapter.ItemListBooksAdapter
import com.example.do_an_app.callback.CallBack
import com.example.do_an_app.databinding.FragmentListBooksBinding
import com.example.do_an_app.model.books.Result
import com.example.do_an_app.viewmodel.BooksViewModel
import com.example.do_an_app.viewmodel.LoadingViewModel
import kotlinx.coroutines.launch

class FragmentListBooks : Fragment(), CallBack {

    private lateinit var binding: FragmentListBooksBinding
    private lateinit var adapter: ItemListBooksAdapter
    private lateinit var booksViewModel: BooksViewModel
    private lateinit var loadingViewModel: LoadingViewModel
    private var name = ""
    private var author = ""
    private val list = arrayListOf<Result>()
    private var isLoading = false
    private var page = 1
    private var isLastPage = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBooksBinding.inflate(inflater, container, false)
        loadingViewModel = ViewModelProvider(requireActivity()).get(LoadingViewModel::class.java)
        binding.loading.visibility = View.VISIBLE

        binding.imgBack.setOnClickListener {
            loadingViewModel.isLoading.value = true
            findNavController().navigate(R.id.action_fragmentListBooks_to_fragmentHome)
        }

        adapter = ItemListBooksAdapter(list, this)
        binding.rvListBooks.adapter = adapter
        binding.rvListBooks.layoutManager =
            GridLayoutManager(FragmentListBooks().context, 3)

        if (arguments != null) {
            name = arguments?.getString("name").toString()
            binding.txtSearchName.setText(name)

        } else {
            name = ""
        }

        if (name == "") {
            booksViewModel = ViewModelProvider(requireActivity()).get(BooksViewModel::class.java)
            booksViewModel.getBooks(1, 10, "", "", "")
            booksViewModel.dataBooks.observe(viewLifecycleOwner) {
                if (it != null) {
//                    list.clear()
                    list.addAll(it.data.result)
                    adapter.notifyDataSetChanged()

                }
                // Ẩn progressBar khi kết thúc load dữ liệu
                binding.loading.visibility = View.GONE
            }
        } else {
            booksViewModel = ViewModelProvider(requireActivity()).get(BooksViewModel::class.java)
            booksViewModel.getBooks(1, 10, name, "", "")
            booksViewModel.dataBooks.observe(viewLifecycleOwner) {
                if (it != null) {
//                    list.clear()
                    list.addAll(it.data.result)
                    adapter.notifyDataSetChanged()

                }
                // Ẩn progressBar khi kết thúc load dữ liệu
                binding.loading.visibility = View.GONE
            }
        }

        binding.btnSearch.setOnClickListener {
            binding.loading.visibility = View.VISIBLE
            booksViewModel = ViewModelProvider(requireActivity()).get(BooksViewModel::class.java)
            booksViewModel.getBooks(
                1,
                10,
                binding.txtSearchName.text.toString(),
                binding.txtSearchAuthor.text.toString(),
                ""
            )
            booksViewModel.dataBooks.observe(viewLifecycleOwner) {
                if (it != null) {
//                    list.clear()
                    list.addAll(it.data.result)
                    adapter.notifyDataSetChanged()

                }
                // Ẩn progressBar khi kết thúc load dữ liệu
                binding.loading.visibility = View.GONE
            }
        }


        binding.rvListBooks.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItem =
                    (binding.rvListBooks.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                val totalItemCount = adapter.itemCount
                if (isLastPage == true){
                    return
                }else{
                    if (!isLoading && totalItemCount <= (lastVisibleItem + 1)) {
                        isLoading = true
                        page += 1
                        loadMoreData()
                    }
                }
            }
        })

//        val visibleItemCount = intArrayOf(0)
//        val totalItemCount = intArrayOf(0)
//        val pastVisiblesItems = intArrayOf(0)
//        binding.rvListBooks.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                if (dy > 0) {
//                    visibleItemCount[0] = binding.rvListBooks.childCount
//                    totalItemCount[0] = binding.rvListBooks.layoutManager!!.itemCount
//                    pastVisiblesItems[0] =
//                        (binding.rvListBooks.layoutManager as GridLayoutManager?)!!.findFirstVisibleItemPosition()
//                    if (isLastPage) {
//                        return
//                    } else {
//                        if (visibleItemCount[0] + pastVisiblesItems[0] >= totalItemCount[0]) {
//                            loadMoreData()
//                            Log.d("loadmore","load")
//                        }
//                    }
//                }
//            }
//        })

        return binding.root
    }

    private fun loadMoreData() {
        lifecycleScope.launch {
            booksViewModel.getBooks(page, 10, binding.txtSearchName.text.toString(), binding.txtSearchAuthor.text.toString(), "")
            booksViewModel.dataBooks.observe(viewLifecycleOwner) {
                if (it != null) {
                    if (it.data.result.size == 0) {
                        isLastPage = true
                    } else {
                        for(item in it.data.result){
                            list.add(item)
                        }
                        adapter.notifyItemInserted(list.size)

//                        adapter.addItems(it.data.result)

                        isLastPage = false
                    }
                }else{
                    return@observe
                }
            }
            isLoading = false
        }
    }

    override fun onClick(books: Result) {
        val bundle = Bundle()
        bundle.putString("code", books.code)
        findNavController().navigate(R.id.action_fragmentListBooks_to_fragmentDetailBooks, bundle)
        list.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_fragmentListBooks_to_fragmentHome)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
}