package com.example.tinkofftesttask.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.tinkofftesttask.databinding.ItemViewPagerBinding
import com.example.tinkofftesttask.domain.model.Gif

class PagingAdapter() :
    PagingDataAdapter<Gif, PagingAdapter.GifViewHolder>(ARTICLE_COMPARATOR) {

    companion object {
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<Gif>() {
            override fun areContentsTheSame(oldItem: Gif, newItem: Gif): Boolean =
                oldItem == newItem


            override fun areItemsTheSame(oldItem: Gif, newItem: Gif): Boolean =
                oldItem.id == newItem.id
        }
    }


    class GifViewHolder(
        private val binding: ItemViewPagerBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        /*private val circularProgressDrawable = CircularProgressDrawable(binding.root.context)

        init {
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
        }*/

        fun bind(gif: Gif) {
            binding.apply {
                Glide.with(root)
                    .load(gif.gifURL)
                    .thumbnail(Glide.with(root).load(gif.previewURL))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(cardImage)

                gifTitle.text = gif.description
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