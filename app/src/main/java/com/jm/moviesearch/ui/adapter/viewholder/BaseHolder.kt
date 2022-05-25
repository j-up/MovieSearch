package com.jm.moviesearch.ui.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jm.moviesearch.data.NaverEntity

abstract class BaseViewHolder<ITEM>(view : View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(item: ITEM)
}

interface HolderClickListener {
    fun itemClickListener(naverEntity: NaverEntity)
    fun bookmarkClickListener(
        bookmark: NaverEntity.Bookmark,
        isBookmark: Boolean = false
    )
}