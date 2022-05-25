package com.jm.moviesearch.ui.detail

sealed class DetailUiState {
    data class Loading(val nothing: Nothing? = null) : DetailUiState()
    data class Finish(val nothing: Nothing? = null) : DetailUiState()
    data class InsertBookmark(val index: Long) : DetailUiState()
    data class DeleteBookmark(val index: Int) : DetailUiState()
    data class Error(val error: String) : DetailUiState()
}
