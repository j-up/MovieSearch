package com.jm.moviesearch.di

import com.jm.moviesearch.ui.bookmark.BookmarkViewModel
import com.jm.moviesearch.ui.detail.DetailViewModel
import com.jm.moviesearch.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { BookmarkViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}