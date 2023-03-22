package com.example.do_an_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.do_an_app.callback.CallbackVoucher
import com.example.do_an_app.databinding.ItemVoucherBinding
import com.example.do_an_app.model.voucher.Result

class VoucherAdapter(private val list: List<Result>, private val callback: CallbackVoucher) : RecyclerView.Adapter<VoucherAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemVoucherBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result){
            binding.voucher = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemVoucherBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])

        holder.binding.cdVoucher.setOnClickListener {
            callback.onClick(list[position])
        }

        holder.binding.cdVoucher.setOnLongClickListener {
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
}