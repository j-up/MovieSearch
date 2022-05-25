package com.jm.moviesearch.api

import com.jm.moviesearch.di.networkModules
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class NaverApiTest : KoinTest {
    private val naverApi: NaverApi by inject()

    @get:Rule
    val testRule = KoinTestRule.create {
        modules(networkModules)
    }
    @Test
    fun `API_호출시_성공적으로_반환된다`() = runBlocking {
        val result = naverApi.getNaverMovie("소리").execute()
        Assert.assertTrue(result.isSuccessful)
        println(result.body())
    }

}