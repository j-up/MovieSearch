package com.jm.moviesearch.ui.main

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
import com.jm.moviesearch.databinding.ActivityMainBinding
import com.jm.moviesearch.ui.BaseActivity
import com.jm.moviesearch.ui.adapter.MovieAdapter
import com.jm.moviesearch.ui.adapter.viewholder.HolderClickListener
import com.jm.moviesearch.ui.bookmark.BookmarkActivity
import com.jm.moviesearch.ui.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val mainViewModel: MainViewModel by viewModel()
    private val requestManager: RequestManager by lazy {
        Glide.with(this)
    }

    private lateinit var movieAdapter: MovieAdapter

    private val tag = this::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding.apply {
            lifecycleOwner = this@MainActivity
            mainViewModel = this@MainActivity.mainViewModel
            rvMovieList.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL))
        }

        initUi()
        initViewModel()
    }

    private fun initUi() {
        val holderClickListener = object: HolderClickListener{
            override fun itemClickListener(naverEntity: NaverEntity) {
                if(naverEntity is NaverEntity.Movie) {
                    Log.v(tag, "item click title:${naverEntity.title}")
                    startActivity(
                        Intent(this@MainActivity, DetailActivity::class.java)
                            .putExtra(DetailActivity.INTENT_PUT_EXTRA_KEY_MOVIE, naverEntity)
                    )
                }
            }

            override fun bookmarkClickListener(
                bookmark: NaverEntity.Bookmark,
                isBookmark: Boolean
            ) {
                Log.v(tag, "bookmark click title:${bookmark.title}")

                if(isBookmark) {
                    mainViewModel.deleteBookmark(bookmark)
                } else {
                    mainViewModel.insertBookmark(bookmark)
                }
            }
        }

        movieAdapter = MovieAdapter(requestManager, holderClickListener)
        binding.rvMovieList.adapter = movieAdapter

        binding.llBookmarkLayer.setOnClickListener {
            startActivity(
                Intent(this, BookmarkActivity::class.java)
            )
        }
    }

    private fun initViewModel() {
        mainViewModel.bookmarkListLiveData.observe(this) { bookmarkList ->
            Log.d(tag, "bookmark update")

            binding.pbLoddingBar.visibility = View.GONE

            if (this::movieAdapter.isInitialized) {
                movieAdapter.onBookmarkRefresh(bookmarkList.map { bookmark ->
                    bookmark.toMovie()
                })
            } else {
                binding.tvEmptyGuide.visibility = View.VISIBLE
            }
        }

        mainViewModel.viewStateLiveData.observe(this) { mainUiState ->
            Log.d(tag, "ui state: ${mainUiState.javaClass.simpleName}")

            when(mainUiState) {
                is MainUiState.Loading -> {
                    binding.pbLoddingBar.visibility = View.VISIBLE
                    binding.tvEmptyGuide.visibility = View.GONE
                }

                is MainUiState.OnMovieListLoad -> {
                    binding.pbLoddingBar.visibility = View.GONE
                    binding.tvEmptyGuide.visibility = View.GONE

                    if (mainUiState.movieList.isNotEmpty()) {
                        movieAdapter.addAll(mainUiState.movieList)
                        Log.d(tag, mainUiState.movieList.toString())
                    } else {
                        binding.tvEmptyGuide.visibility = View.VISIBLE
                        movieAdapter.clear()
                    }
                }

                is MainUiState.TextAllRemove -> {

                }

                is MainUiState.InsertBookmark -> {

                }

                is MainUiState.DeleteBookmark -> {

                }

                is MainUiState.Error -> {
                    Log.d(tag, "error: ${mainUiState.error}")
                    movieAdapter.clear()

                    binding.tvEmptyGuide.visibility = View.VISIBLE
                    binding.pbLoddingBar.visibility = View.INVISIBLE
                }
            }

        }
    }
}