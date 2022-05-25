package com.jm.moviesearch.data.remote

import com.jm.moviesearch.api.NaverApi
import com.jm.moviesearch.base.BaseTest
import com.jm.moviesearch.source.RemoteNaverDataSource
import com.jm.moviesearch.util.DummyData
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class RemoteNaverDataSourceTest: BaseTest() {

    @MockK
    lateinit var naverApi: NaverApi

    lateinit var remoteNaverDataSource: RemoteNaverDataSource

    override fun setup() {
        super.setup()
        naverApi = mockk()
        remoteNaverDataSource = RemoteNaverDataSource(naverApi)
    }

    @Test
    fun `getNaverApi_호출시_성공적으로_MovieResponse_반환된다`() = runBlocking {
        // given
        val call:Call<MovieResponse> = mockk()
        every { call.execute() }.returns(Response.success(DummyData.movieResponse))
        coEvery { naverApi.getNaverMovie(DummyData.keyword) }.returns(call)

        // when
        val movieResponse:Response<MovieResponse> = remoteNaverDataSource.getNaverMovie(DummyData.keyword).execute()

        // then
        Assert.assertEquals(true, movieResponse.isSuccessful)
        Assert.assertEquals(DummyData.movieList, movieResponse.body()!!.movieList)
    }

    @Test
    fun `getNaverApi_호출시_실패하여_isSuccessful_false_반환된다`() = runBlocking {
        // given
        val call:Call<MovieResponse> = mockk()
        every { call.execute() }.returns(Response.error(400, ResponseBody.create(MediaType.parse("text/plain;charset=utf-8"), "text")))
        coEvery { naverApi.getNaverMovie(DummyData.keyword) }.returns(call)

        // when
        val movieResponse:Response<MovieResponse> = remoteNaverDataSource.getNaverMovie(DummyData.keyword).execute()

        // then
        Assert.assertEquals(false, movieResponse.isSuccessful)
    }

    @Test
    fun `insertBookmark_호출시_지원하지않아_UnsupportedOperationException_발생한다`() = runBlocking {
        // given
        var isUnsupportedOperationException: Boolean = false

        // when
        try {
            remoteNaverDataSource.insertBookmark(DummyData.bookmarkItem)
        } catch (e: UnsupportedOperationException) {
            isUnsupportedOperationException = true
        }

        // then
        Assert.assertEquals(true, isUnsupportedOperationException)
    }

    @Test
    fun `getAllBookmark_호출시_지원하지않아_UnsupportedOperationException_발생한다`() = runBlocking {
        // given
        var isUnsupportedOperationException:Boolean = false

        // when
        try {
            remoteNaverDataSource.getAllBookmark()
        } catch (e: UnsupportedOperationException) {
            isUnsupportedOperationException = true
        }

        // then
        Assert.assertEquals(true, isUnsupportedOperationException)
    }

    @Test
    fun `getAllBookmarkLiveData_호출시_지원하지않아_UnsupportedOperationException_발생한다`() = runBlocking {
        // given
        var isUnsupportedOperationException:Boolean = false

        // when
        try {
            remoteNaverDataSource.getAllBookmarkLiveData()
        } catch (e: UnsupportedOperationException) {
            isUnsupportedOperationException = true
        }

        // then
        Assert.assertEquals(true, isUnsupportedOperationException)
    }

    @Test
    fun `deleteBookmark_호출시_지원하지않아_UnsupportedOperationException_발생한다`() = runBlocking {
        // given
        var isUnsupportedOperationException: Boolean = false

        // when
        try {
            remoteNaverDataSource.deleteBookmark(
                DummyData.bookmarkItem.title,
                DummyData.bookmarkItem.image,
                DummyData.bookmarkItem.link
            )
        } catch (e: UnsupportedOperationException) {
            isUnsupportedOperationException = true
        }

        // then
        Assert.assertEquals(true, isUnsupportedOperationException)
    }
}