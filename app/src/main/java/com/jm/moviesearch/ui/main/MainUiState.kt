package com.jm.moviesearch.ui.main

import com.jm.moviesearch.data.NaverEntity

sealed class MainUiState {
    data class Loading(val nothing: Nothing? = null) : MainUiState()
    data class OnMovieListLoad(val movieList: List<NaverEntity>) : MainUiState()
    data class TextAllRemove(val nothing: Nothing? = null) : MainUiState()
    data class InsertBookmark(val index: Long) : MainUiState()
    data class DeleteBookmark(val index: Int) : MainUiState()
    data class Error(val error: String) : MainUiState()
}