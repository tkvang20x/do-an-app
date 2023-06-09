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
import com.example.do_an_app.adapter.ItemListGroupsAdapter
import com.example.do_an_app.callback.CallBackGroups
import com.example.do_an_app.databinding.FragmentGroupsBinding
import com.example.do_an_app.model.group.Result
import com.example.do_an_app.viewmodel.BooksViewModel
import com.example.do_an_app.viewmodel.GroupsViewModel

class FragmentGroups : Fragment(), CallBackGroups {
    private lateinit var binding: FragmentGroupsBinding
    private lateinit var groupsViewModel: GroupsViewModel
    private val list = arrayListOf<Result>()
    private lateinit var adapter: ItemListGroupsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupsBinding.inflate(inflater, container, false)
        binding.loading.visibility = View.VISIBLE

        adapter = ItemListGroupsAdapter(list, this)
        binding.rvListGroups.adapter = adapter
        binding.rvListGroups.layoutManager =
            GridLayoutManager(FragmentListBooks().context, 2 )

        groupsViewModel = ViewModelProvider(requireActivity()).get(GroupsViewModel::class.java)
        groupsViewModel.getGroups()
        groupsViewModel.dataGroups.observe(viewLifecycleOwner) {
            if (it != null) {
                list.clear()
                list.addAll(it.data.result)
                adapter.notifyDataSetChanged()

            }
            binding.loading.visibility = View.GONE
        }

        return binding.root
    }

    override fun onClick(groups: Result) {
        val bundle = Bundle()
        bundle.putString("group_name", groups.group_name)
        bundle.putString("group_code", groups.group_code)
        findNavController().navigate(R.id.action_fragmentGroups_to_fragmentListGroupsBooks, bundle)
        list.clear()
    }


}