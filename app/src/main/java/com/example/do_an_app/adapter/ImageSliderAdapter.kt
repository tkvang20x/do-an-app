package com.example.do_an_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.do_an_app.R
import com.example.do_an_app.databinding.ItemSlideImageBinding
import com.example.do_an_app.model.Image
import com.smarteist.autoimageslider.SliderViewAdapter

class ImageSliderAdapter(private val list: List<Image>) : SliderViewAdapter<ImageSliderAdapter.SliderAdapterViewHolder>() {

    class SliderAdapterViewHolder(private val binding: ItemSlideImageBinding) : SliderViewAdapter.ViewHolder(binding.root) {
        fun bind(item : Image){
            Glide.with(binding.myimage)
                .load(item.path)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher)
                .into(binding.myimage)
        }
    }

    override fun getCount(): Int {
       return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderAdapterViewHolder {
            return SliderAdapterViewHolder(ItemSlideImageBinding.inflate(LayoutInflater.from(parent?.context), parent, false))
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterViewHolder?, position: Int) {
            viewHolder?.bind(list[position])
    }
}