package com.jm.moviesearch.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jm.moviesearch.data.NaverEntity
import com.jm.moviesearch.repository.NaverRepository
import com.jm.moviesearch.ui.BaseViewModel
import kotlinx.coroutines.*

class DetailViewModel(private val naverRepository: NaverRepository): BaseViewModel<DetailUiState>() {

    override var _viewStateLiveData: MutableLiveData<DetailUiState> = MutableLiveData<DetailUiState>()
    override var viewStateLiveData: LiveData<DetailUiState> = _viewStateLiveData

    override var coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch(Dispatchers.Main) {
            _viewStateLiveData.value = DetailUiState.Error(throwable.message ?: "fail")
        }

        throwable.printStackTrace()
    }

    val bookmarkListLiveData: LiveData<List<NaverEntity.Bookmark>> = naverRepository.getAllBookmarkLiveData()

    private val tag = this::class.simpleName

    fun onCloseClick() {
        _viewStateLiveData.value = DetailUiState.Finish()
    }

    fun onBookmarkClick(movie: NaverEntity.Movie) {
        if(movie.isBookmark) {
            deleteBookmark(movie.toBookmark())
        } else {
            insertBookmark(movie.toBookmark())
        }
    }

    fun insertBookmark(bookmark: NaverEntity.Bookmark) {
        _viewStateLiveData.value = DetailUiState.Loading()

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            delay(300)
            var result: Long = naverRepository.insertBookmark(bookmark)
            Log.d(tag, "insert index: $result")
            withContext(Dispatchers.Main) {
                _viewStateLiveData.value = DetailUiState.InsertBookmark(result)
            }

        }
    }

    fun deleteBookmark(bookmark: NaverEntity.Bookmark) {
        _viewStateLiveData.value = DetailUiState.Loading()

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            delay(300)
            var result: Int = naverRepository.deleteBookmark(bookmark.title, bookmark.image, bookmark.link)
            Log.d(tag, "delete row count: $result")

            withContext(Dispatchers.Main) {
                _viewStateLiveData.value = DetailUiState.DeleteBookmark(result)
            }
        }
    }
}