package com.jm.moviesearch.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.jm.moviesearch.data.NaverEntity
import com.jm.moviesearch.ui.adapter.viewholder.HolderClickListener
import com.jm.moviesearch.ui.adapter.viewholder.BookmarkViewHolder
import com.jm.moviesearch.ui.adapter.viewholder.MovieViewHolder

class MovieAdapter(
    private val requestManager: RequestManager,
    private val holderClickListener: HolderClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val entityList:MutableList<NaverEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_HOLDER_TYPE_MOVIE -> MovieViewHolder.create(
                requestManager,
                holderClickListener,
                parent
            )
            VIEW_HOLDER_TYPE_BOOKMARK -> BookmarkViewHolder.create(
                requestManager,
                holderClickListener,
                parent
            )
            else -> throw NoClassDefFoundError()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when (val item = entityList[position]) {
            is NaverEntity.Movie -> (holder as MovieViewHolder).bind(item)
            is NaverEntity.Bookmark -> (holder as BookmarkViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int {
        return entityList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (entityList[position]) {
            is NaverEntity.Movie -> VIEW_HOLDER_TYPE_MOVIE
            is NaverEntity.Bookmark -> VIEW_HOLDER_TYPE_BOOKMARK
        }
    }

    fun onBookmarkRefresh(bookmarkToMovieList: List<NaverEntity.Movie>) {
        if(entityList.isNotEmpty()) {

            val naverEntityList:List<NaverEntity> = entityList.filterIsInstance<NaverEntity.Movie>().map { movie ->
                movie.isBookmark = bookmarkToMovieList.contains(movie)
                movie as NaverEntity
            }
            addAll(naverEntityList)
        }
    }

    fun addAll(list: List<NaverEntity>) {
        entityList.clear()
        entityList.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        entityList.clear()
        notifyDataSetChanged()
    }


    companion object {
        const val VIEW_HOLDER_TYPE_MOVIE = 101
        const val VIEW_HOLDER_TYPE_BOOKMARK = 102
    }

}