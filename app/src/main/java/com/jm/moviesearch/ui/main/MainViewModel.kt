package com.jm.moviesearch.ui.main

import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jm.moviesearch.data.NaverEntity
import com.jm.moviesearch.repository.NaverRepository
import com.jm.moviesearch.ui.BaseViewModel
import kotlinx.coroutines.*

class MainViewModel(private val naverRepository: NaverRepository): BaseViewModel<MainUiState>() {
    override var _viewStateLiveData:MutableLiveData<MainUiState> = MutableLiveData<MainUiState>()
    override var viewStateLiveData: LiveData<MainUiState> = _viewStateLiveData

    override var coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch(Dispatchers.Main) {
            _viewStateLiveData.value = MainUiState.Error(throwable.message ?: "fail")
        }

        throwable.printStackTrace()
    }

    val inputKeyword:MutableLiveData<String> = MutableLiveData<String>()

    val bookmarkListLiveData: LiveData<List<NaverEntity.Bookmark>> = naverRepository.getAllBookmarkLiveData()

    private val tag = this::class.simpleName

    fun getNaverMovie(keyword: String) {
        _viewStateLiveData.value = MainUiState.Loading()
        Log.v(tag,"search: $keyword")

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val result = naverRepository.getNaverMovie(keyword).execute()

            val bookmarkList = bookmarkListLiveData.value?.map { bookmark ->
                bookmark.toMovie()
            }?: emptyList()

            delay(300)
            withContext(Dispatchers.Main) {
                when (result.isSuccessful && result.body() != null) {
                    true -> {
                        val naverEntityList: List<NaverEntity> =
                            result.body()!!.movieList.map { movie ->
                                movie.isBookmark = bookmarkList.contains(movie)
                                movie as NaverEntity
                            }
                        _viewStateLiveData.value = MainUiState.OnMovieListLoad(naverEntityList)
                    }
                    false -> {
                        _viewStateLiveData.value = MainUiState.Error("getNaverMovie fail")
                    }
                }

            }
        }
    }

    fun insertBookmark(bookmark: NaverEntity.Bookmark) {
        _viewStateLiveData.value = MainUiState.Loading()

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            delay(300)
            var result: Long = naverRepository.insertBookmark(bookmark)
            Log.d(tag, "insert index: $result")

            withContext(Dispatchers.Main) {
                _viewStateLiveData.value = MainUiState.InsertBookmark(result)
            }

        }
    }

    fun deleteBookmark(bookmark: NaverEntity.Bookmark) {
        _viewStateLiveData.value = MainUiState.Loading()

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            delay(300)
            var result: Int = naverRepository.deleteBookmark(bookmark.title, bookmark.image, bookmark.link)
            Log.d(tag, "delete row count: $result")

            withContext(Dispatchers.Main) {
                _viewStateLiveData.value = MainUiState.DeleteBookmark(result)
            }
        }
    }

    fun onAllRemoveClick() {
        inputKeyword.value = ""
        _viewStateLiveData.value = MainUiState.TextAllRemove()
    }

    fun onEditorAction(view: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            getNaverMovie(inputKeyword.value?: "")
            return true
        }

        return false
    }

}