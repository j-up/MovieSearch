package com.jm.moviesearch.ui.bookmark

import android.util.Log
import androidx.lifecycle.*
import com.jm.moviesearch.data.NaverEntity
import com.jm.moviesearch.repository.NaverRepository
import com.jm.moviesearch.ui.BaseViewModel
import kotlinx.coroutines.*

class BookmarkViewModel(private val naverRepository: NaverRepository): BaseViewModel<BookmarkUiState>() {
    override var _viewStateLiveData: MutableLiveData<BookmarkUiState> = MutableLiveData<BookmarkUiState>()
    override var viewStateLiveData: LiveData<BookmarkUiState> = _viewStateLiveData

    override var coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch(Dispatchers.Main) {
            _viewStateLiveData.value = BookmarkUiState.Error(throwable.message ?: "fail")
        }

        throwable.printStackTrace()
    }

    val bookmarkListLiveData: LiveData<List<NaverEntity.Bookmark>> = naverRepository.getAllBookmarkLiveData()

    private val tag = this::class.simpleName

    fun onCloseClick() {
        _viewStateLiveData.value = BookmarkUiState.Finish()
    }

    fun deleteBookmark(bookmark: NaverEntity.Bookmark) {
        _viewStateLiveData.value = BookmarkUiState.Loading()

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            delay(300)
            val result: Int = naverRepository.deleteBookmark(bookmark.title, bookmark.image, bookmark.link)
            Log.d(tag, "delete row count: $result")

            withContext(Dispatchers.Main) {
                _viewStateLiveData.value = BookmarkUiState.DeleteBookmark(result)
            }
        }
    }
}