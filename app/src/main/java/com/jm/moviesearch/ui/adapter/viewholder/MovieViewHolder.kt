package com.jm.moviesearch.ui.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.jm.moviesearch.R
import com.jm.moviesearch.data.NaverEntity
import com.jm.moviesearch.databinding.ItemMovieBinding

class MovieViewHolder(
    private val requestManager: RequestManager,
    private val holderClickListener: HolderClickListener,
    private val binding: ItemMovieBinding
): BaseViewHolder<NaverEntity.Movie>(binding.root) {

    override fun bind(item: NaverEntity.Movie) {
        with(binding) {
            movie = item

            root.setOnClickListener {
                holderClickListener.itemClickListener(item)
            }

            ivBookmark.setOnClickListener {
                holderClickListener.bookmarkClickListener(item.toBookmark(), item.isBookmark)
            }

            requestManager.load(item.image)
                .error(root.context.getDrawable(R.drawable.no_image_icon))
                .into(ivImage)
        }

    }

    companion object {
        fun create(
            requestManager: RequestManager,
            holderClickListener: HolderClickListener,
            parent: ViewGroup
        ): MovieViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
            return MovieViewHolder(requestManager, holderClickListener, binding)
        }
    }
}