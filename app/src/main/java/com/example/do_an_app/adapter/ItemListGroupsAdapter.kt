package com.example.do_an_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.do_an_app.callback.CallBackGroups
import com.example.do_an_app.databinding.ItemGroupBinding
import com.example.do_an_app.model.group.Result
class ItemListGroupsAdapter(private val list: List<Result>, private val callback: CallBackGroups) : RecyclerView.Adapter<ItemListGroupsAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemGroupBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result){
            binding.groups = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(list[position])

        holder.binding.csGroups.setOnClickListener {
            callback.onClick(list[position])
        }

        holder.binding.csGroups.setOnLongClickListener {
            callback.onLongClick(list[position])

            return@setOnLongClickListener true
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}