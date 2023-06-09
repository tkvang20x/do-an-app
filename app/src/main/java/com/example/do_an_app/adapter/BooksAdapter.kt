package com.example.do_an_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.do_an_app.callback.CallBack
import com.example.do_an_app.databinding.ItemBooksBinding
import com.example.do_an_app.model.books.Result

class BooksAdapter(private val list: ArrayList<Result>, private val callback: CallBack) : RecyclerView.Adapter<BooksAdapter.ViewHolder>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    class ViewHolder(val binding: ItemBooksBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result){
            binding.books = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBooksBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(list[position])

        holder.binding.csBooks.setOnClickListener {
            callback.onClick(list[position])
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