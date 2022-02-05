package com.example.tinkofftesttask.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tinkofftesttask.data.model.GifDto
import com.example.tinkofftesttask.databinding.ItemViewPagerBinding

class ViewPagerAdapter() :
    RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    var gifs: List<GifDto> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewPagerViewHolder(private val binding: ItemViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(gif: GifDto) {
            Glide.with(binding.root).asGif().load(gif.gifURL).into(binding.cardImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val binding =
            ItemViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val currentItem = gifs[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = gifs.size
}