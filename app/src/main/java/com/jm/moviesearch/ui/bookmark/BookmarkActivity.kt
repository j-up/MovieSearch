package com.jm.moviesearch.ui.bookmark

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.jm.moviesearch.R
import com.jm.moviesearch.data.NaverEntity
import com.jm.moviesearch.databinding.ActivityBookmarkBinding
import com.jm.moviesearch.ui.BaseActivity
import com.jm.moviesearch.ui.adapter.MovieAdapter
import com.jm.moviesearch.ui.adapter.viewholder.HolderClickListener
import com.jm.moviesearch.ui.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookmarkActivity : BaseActivity<ActivityBookmarkBinding>(R.layout.activity_bookmark) {
    private val bookmarkViewModel: BookmarkViewModel by viewModel()
    private val requestManager: RequestManager by lazy {
        Glide.with(this)
    }

    private val tag = this::class.simpleName

    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding.apply {
            lifecycleOwner = this@BookmarkActivity
            bookmarkViewModel = this@BookmarkActivity.bookmarkViewModel
            rvBookmarkList.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL))
        }

        initUi()
        initViewModel()
    }

    private fun initUi() {
        val holderClickListener = object: HolderClickListener {
            override fun itemClickListener(naverEntity: NaverEntity) {
                if(naverEntity is NaverEntity.Bookmark) {
                    startActivity(
                        Intent(this@BookmarkActivity, DetailActivity::class.java)
                            .putExtra(DetailActivity.INTENT_PUT_EXTRA_KEY_MOVIE, naverEntity.toMovie().copy(isBookmark = true))
                    )
                    Log.v(tag, "item click movie:${naverEntity.title}")
                }
            }
            override fun bookmarkClickListener(
                bookmark: NaverEntity.Bookmark,
                isBookmark: Boolean
            ) {
                bookmarkViewModel.deleteBookmark(bookmark)
            }
        }

        movieAdapter = MovieAdapter(requestManager, holderClickListener)
        binding.rvBookmarkList.adapter = movieAdapter

    }

    private fun initViewModel() {
        bookmarkViewModel.bookmarkListLiveData.observe(this) { bookmarkList ->
            binding.pbLoddingBar.visibility = View.GONE

            if (this::movieAdapter.isInitialized && bookmarkList.isNotEmpty()) {
                movieAdapter.addAll(bookmarkList.map { bookmark ->
                    bookmark as NaverEntity
                })
            } else {
                movieAdapter.clear()
                binding.tvEmptyGuide.visibility = View.VISIBLE
            }
        }

        bookmarkViewModel.viewStateLiveData.observe(this) { bookmarkUiState ->
            Log.d(tag, "ui state: ${bookmarkUiState.javaClass.simpleName}")

            when(bookmarkUiState) {
                is BookmarkUiState.Loading -> {
                    binding.pbLoddingBar.visibility = View.VISIBLE
                    binding.tvEmptyGuide.visibility = View.GONE
                }

                is BookmarkUiState.OnBookmarkListLoad -> {
                    binding.pbLoddingBar.visibility = View.GONE
                    binding.tvEmptyGuide.visibility = View.GONE
                }

                is BookmarkUiState.Finish -> {
                    finish()
                }

                is BookmarkUiState.DeleteBookmark -> {

                }

                is BookmarkUiState.Error -> {
                    binding.tvEmptyGuide.visibility = View.VISIBLE
                    binding.pbLoddingBar.visibility = View.GONE
                }
            }
        }
    }
}