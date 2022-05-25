package com.jm.moviesearch.base

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import io.mockk.impl.annotations.MockK

open class BaseViewModelTest<VM : ViewModel, State> : BaseTest() {

    protected lateinit var viewModel: VM

    @MockK
    protected lateinit var viewStateObserver: Observer<State>
}