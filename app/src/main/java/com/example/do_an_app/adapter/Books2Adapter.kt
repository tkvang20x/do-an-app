package com.example.do_an_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.do_an_app.callback.CallBack
import com.example.do_an_app.callback.CallBack2
import com.example.do_an_app.databinding.ItemBooks2Binding
import com.example.do_an_app.databinding.ItemBooksBinding
import com.example.do_an_app.model.books.Result
import com.example.do_an_app.model.chart.BooksCount

class Books2Adapter(private val list: ArrayList<BooksCount>, private val callback: CallBack2) : RecyclerView.Adapter<Books2Adapter.ViewHolder>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    class ViewHolder(val binding: ItemBooks2Binding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BooksCount){
            binding.bookCount = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBooks2Binding.inflate(LayoutInflater.from(parent.context), parent, false))
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

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        holder.setIsRecyclable(false)
        super.onViewDetachedFromWindow(holder)
    }
    override fun getItemViewType(position: Int): Int {
        return if (list.get(position) == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }
}