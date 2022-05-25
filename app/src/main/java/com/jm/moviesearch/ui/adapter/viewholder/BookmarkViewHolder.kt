package com.jm.moviesearch.ui.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.jm.moviesearch.R
import com.jm.moviesearch.data.NaverEntity
import com.jm.moviesearch.databinding.ItemBookmarkBinding

class BookmarkViewHolder(
    private val requestManager: RequestManager,
    private val holderClickListener: HolderClickListener,
    private val binding: ItemBookmarkBinding
): BaseViewHolder<NaverEntity.Bookmark>(binding.root) {

    override fun bind(item: NaverEntity.Bookmark) {
        with(binding) {
            bookmark = item

            root.setOnClickListener {
                holderClickListener.itemClickListener(item)
            }

            ivBookmark.setOnClickListener {
                holderClickListener.bookmarkClickListener(item)
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
        ): BookmarkViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemBookmarkBinding.inflate(layoutInflater, parent, false)
            return BookmarkViewHolder(requestManager, holderClickListener, binding)
        }
    }
}