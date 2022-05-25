package com.jm.moviesearch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel<UiState>: ViewModel() {
    protected abstract var _viewStateLiveData: MutableLiveData<UiState>
    abstract var viewStateLiveData: LiveData<UiState>

    protected abstract var coroutineExceptionHandler: CoroutineExceptionHandler

}