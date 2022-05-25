package com.jm.moviesearch.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.jm.moviesearch.R
import com.jm.moviesearch.data.NaverEntity
import com.jm.moviesearch.databinding.ActivityDetailBinding
import com.jm.moviesearch.ui.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity:BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {
    private val detailViewModel: DetailViewModel by viewModel()
    private val requestManager: RequestManager by lazy {
        Glide.with(this)
    }
    private val tag = this::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val movie:NaverEntity.Movie? = intent.getParcelableExtra(INTENT_PUT_EXTRA_KEY_MOVIE)
        if(movie == null) {
            Toast.makeText(this, "not found movie", Toast.LENGTH_SHORT).show()
            finish()
        }

        Log.d(tag,"movie:${movie.toString()}")

        binding.apply {
            lifecycleOwner = this@DetailActivity
            detailViewModel = this@DetailActivity.detailViewModel
        }

        initUi(movie!!)
        initViewModel(movie!!)
    }

    private fun initUi(movie: NaverEntity.Movie) {
        binding.apply {
            this.movie = movie

            requestManager.load(movie.image)
                .error(root.context.getDrawable(R.drawable.no_image_icon))
                .into(ivImage)

            webView.apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                loadUrl(movie.link)
            }
        }
    }

    private fun initViewModel(movie: NaverEntity.Movie) {
        detailViewModel.bookmarkListLiveData.observe(this) { bookmarkList ->
            binding.pbLoddingBar.visibility = View.GONE

            val bookmarkToMovieList = bookmarkList.map { bookmark ->
                bookmark.toMovie()
            }

            movie.isBookmark = bookmarkToMovieList.contains(movie)
            binding.movie = movie
        }

        detailViewModel.viewStateLiveData.observe(this) { detailUiState ->
            Log.d(tag, "ui state: ${detailUiState.javaClass.simpleName}")

            when (detailUiState) {
                is DetailUiState.Loading -> {
                    binding.pbLoddingBar.visibility = View.VISIBLE
                }

                is DetailUiState.Finish -> {
                    finish()
                }

                is DetailUiState.InsertBookmark -> {

                }

                is DetailUiState.DeleteBookmark -> {

                }

                is DetailUiState.Error -> {
                    binding.pbLoddingBar.visibility = View.GONE
                }

            }
        }
    }


    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        const val INTENT_PUT_EXTRA_KEY_MOVIE = "INTENT_PUT_EXTRA_KEY_MOVIE"
    }
}