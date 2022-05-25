package com.jm.moviesearch.ui.bookmark

sealed class BookmarkUiState {
    data class Loading(val nothing: Nothing? = null) : BookmarkUiState()
    data class OnBookmarkListLoad(val nothing: Nothing? = null) : BookmarkUiState()
    data class DeleteBookmark(val index: Int) : BookmarkUiState()
    data class Finish(val nothing: Nothing? = null) : BookmarkUiState()
    data class Error(val error: String) : BookmarkUiState()
}