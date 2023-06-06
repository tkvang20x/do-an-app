package com.example.do_an_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.do_an_app.R
import com.example.do_an_app.adapter.ItemListBooksAdapter
import com.example.do_an_app.callback.CallBack
import com.example.do_an_app.databinding.FragmentListBooksBinding
import com.example.do_an_app.databinding.FragmentListBooksGroupsBinding
import com.example.do_an_app.model.books.Result
import com.example.do_an_app.viewmodel.BooksViewModel

class FragmentListGroupsBooks: Fragment(), CallBack {

    private lateinit var binding: FragmentListBooksGroupsBinding
    private lateinit var adapter: ItemListBooksAdapter
    private lateinit var booksViewModel: BooksViewModel
    private var author = ""
    private var group_name = ""
    private var group_code = ""
    private val list = arrayListOf<Result>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBooksGroupsBinding.inflate(inflater, container, false)
        binding.loading.visibility = View.VISIBLE

        binding.imgBack.setOnClickListener {
//            findNavController().popBackStack()
            findNavController().navigate(R.id.action_fragmentListGroupsBooks_to_fragmentGroups)
        }

        if(arguments != null) {
            group_name = arguments?.getString("group_name").toString()
            group_code = arguments?.getString("group_code").toString()

        }else{
            group_name = ""
            group_code = ""
        }

        binding.tvTitle.text = "Thể loại: " + group_name

        booksViewModel = ViewModelProvider(requireActivity()).get(BooksViewModel::class.java)
        booksViewModel.getBooks(1, 10, "", "", group_code)
        booksViewModel.dataBooks.observe(viewLifecycleOwner) {
            if (it != null) {
                list.clear()
                list.addAll(it.data.result)
                adapter.notifyDataSetChanged()

            }
            // Ẩn progressBar khi kết thúc load dữ liệu
            binding.loading.visibility = View.GONE
        }

        adapter = ItemListBooksAdapter(list, this)
        binding.rvListBooks.adapter = adapter
        binding.rvListBooks.layoutManager =
            GridLayoutManager(FragmentListGroupsBooks().context, 3 )


        binding.btnSearch.setOnClickListener {
            binding.loading.visibility = View.VISIBLE
            booksViewModel = ViewModelProvider(requireActivity()).get(BooksViewModel::class.java)
            booksViewModel.getBooks(1, 10, binding.txtSearchName.text.toString(), binding.txtSearchAuthor.text.toString(), group_code)
            booksViewModel.dataBooks.observe(viewLifecycleOwner) {
                if (it != null) {
                    list.clear()
                    list.addAll(it.data.result)
                    adapter.notifyDataSetChanged()

                }
                // Ẩn progressBar khi kết thúc load dữ liệu
                binding.loading.visibility = View.GONE
            }
        }



        return binding.root
    }

    override fun onClick(books: Result) {
        val bundle = Bundle()
        bundle.putString("code", books.code)
        findNavController().navigate(R.id.action_fragmentListGroupsBooks_to_fragmentDetailBooks, bundle)
        list.clear()
    }

    override fun onLongClick(job: Result) {
        TODO("Not yet implemented")
    }
}