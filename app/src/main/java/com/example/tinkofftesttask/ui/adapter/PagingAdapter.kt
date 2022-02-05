package com.example.tinkofftesttask.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.tinkofftesttask.data.model.GifDto
import com.example.tinkofftesttask.databinding.ItemViewPagerBinding

class PagingAdapter() :
    PagingDataAdapter<GifDto, PagingAdapter.GifViewHolder>(ARTICLE_COMPARATOR) {

    companion object {
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<GifDto>() {
            override fun areContentsTheSame(oldItem: GifDto, newItem: GifDto): Boolean =
                oldItem == newItem


            override fun areItemsTheSame(oldItem: GifDto, newItem: GifDto): Boolean =
                oldItem.id == newItem.id
        }
    }


    class GifViewHolder(
        private val binding: ItemViewPagerBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private val circularProgressDrawable = CircularProgressDrawable(binding.root.context)

        init {
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
        }

        fun bind(gif: GifDto) {
            binding.apply {
                Glide.with(root)
                    .asGif()
                    .load(gif.gifURL)
                    .placeholder(circularProgressDrawable)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(cardImage)
            }
        }
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let {
            holder.bind(currentItem)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): GifViewHolder {
        val binding =
            ItemViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return GifViewHolder(binding)
    }
}