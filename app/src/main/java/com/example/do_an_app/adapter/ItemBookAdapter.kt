package com.example.do_an_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.do_an_app.callback.CallBackItemBook
import com.example.do_an_app.databinding.ItemBookInViewVoucherBinding
import com.example.do_an_app.model.voucher.BooksBorrowed

class ItemBookAdapter (private val list: ArrayList<BooksBorrowed>, private val callback: CallBackItemBook) : RecyclerView.Adapter<ItemBookAdapter.ViewHolder>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    class ViewHolder(val binding: ItemBookInViewVoucherBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BooksBorrowed){
            binding.book = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBookInViewVoucherBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])

        holder.binding.csItemBook.setOnClickListener {
            callback.onClick(list[position])
        }

        holder.binding.csItemBook.setOnLongClickListener {
            callback.onLongClick(list[position])

            return@setOnLongClickListener true
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        holder.setIsRecyclable(false)
        super.onViewDetachedFromWindow(holder)
    }
    override fun getItemViewType(position: Int): Int {
        return if (list.get(position) == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }
}