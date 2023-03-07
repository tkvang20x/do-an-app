package com.example.do_an_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.do_an_app.callback.CallBack
import com.example.do_an_app.databinding.ItemBooksBinding
import com.example.do_an_app.model.books.Result

class BooksAdapter(private val list: List<Result>, private val callback: CallBack) : RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

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

        holder.binding.csBooks.setOnLongClickListener {
            callback.onLongClick(list[position])

            return@setOnLongClickListener true
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}