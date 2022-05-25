package com.jm.moviesearch.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
abstract class BaseTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    open fun setup() {
        Dispatchers.setMain(TestCoroutineDispatcher())
    }
}