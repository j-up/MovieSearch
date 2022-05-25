package com.jm.moviesearch.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.jm.moviesearch.base.BaseViewModelTest
import com.jm.moviesearch.data.NaverEntity
import com.jm.moviesearch.util.DummyData
import com.jm.moviesearch.repository.NaverRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.*
import org.junit.Before
import org.junit.Test
import java.io.IOException

class MainViewModelTest : BaseViewModelTest<MainViewModel, MainUiState>() {

    @MockK
    lateinit var naverRepositoryMock:NaverRepository

    @Before
    override fun setup() {
        super.setup()
        naverRepositoryMock = mockk()
        viewStateObserver = mockk(relaxed = true)

        // 기본적으로 비어있는 북마크 리스트 반환
        every { naverRepositoryMock.getAllBookmarkLiveData().value }.returns(emptyList())

        viewModel = MainViewModel(naverRepositoryMock)
        viewModel.viewStateLiveData.observeForever(viewStateObserver)
    }

    @Test
    fun `getNaverMovie_호출시_성공적으로_호출되어_MainUiState_OnMovieListLoad_응답한다`() = runBlocking {
        // given
        coEvery { naverRepositoryMock.getNaverMovie(DummyData.keyword).execute().isSuccessful }.returns(true)
        coEvery { naverRepositoryMock.getNaverMovie(DummyData.keyword).execute().body() }.returns(
            DummyData.movieResponse)

        // when
        viewModel.getNaverMovie(DummyData.keyword)

        // then
        verify(timeout = 1000L) { viewStateObserver.onChanged(MainUiState.OnMovieListLoad(DummyData.naverEntityList)) }
    }

    @Test
    fun `getNaverMovie_호출시_실패하여_MainUiState_Error_응답한다`() = runBlocking {
        // given
        coEvery { naverRepositoryMock.getNaverMovie(DummyData.keyword).execute().isSuccessful }.returns(false)

        // when
        viewModel.getNaverMovie(DummyData.keyword)

        // then
        verify(timeout = 1000L) { viewStateObserver.onChanged(MainUiState.Error("getNaverMovie fail")) }
    }

    @Test
    fun `getNaverMovie_호출시_Exception_발생하여_coroutineExceptionHandler_MainUiState_Error_응답한다`() = runBlocking {
        // given
        coEvery { naverRepositoryMock.getNaverMovie(DummyData.keyword).execute() }.throws(IOException("request fail"))

        // when
        viewModel.getNaverMovie(DummyData.keyword)

        // then
        verify(timeout = 1000L) { viewStateObserver.onChanged(MainUiState.Error("request fail")) }
    }

    @Test
    fun `getNaverMovie_호출시_북마크가_있을경우_isBookmark_값이_True_주입된다`() = runBlocking {
        // given
        every { naverRepositoryMock.getAllBookmarkLiveData().value }.returns(DummyData.bookmarkList)

        coEvery { naverRepositoryMock.getNaverMovie(DummyData.keyword).execute().isSuccessful }.returns(true)
        coEvery { naverRepositoryMock.getNaverMovie(DummyData.keyword).execute().body() }.returns(
            DummyData.movieResponse)

        // when
        viewModel.getNaverMovie(DummyData.keyword)

        // then
        verify(timeout = 1000L) { viewStateObserver.onChanged(MainUiState.OnMovieListLoad(DummyData.bookmarkedMovieList)) }
    }

    @Test
    fun `텍스트삭제버튼_클릭시_inputKeyword_지워지고_MainUiState_TextAllRemove_응답한다`() = runBlocking {
        // given
        val keywordObserver:Observer<String> = mockk(relaxed = true)
        viewModel.inputKeyword.observeForever(keywordObserver)

        // when
        viewModel.onAllRemoveClick()

        // then
        verify(timeout = 1000L) { keywordObserver.onChanged("") }
        verify(timeout = 1000L) { viewStateObserver.onChanged(MainUiState.TextAllRemove()) }
    }

    @Test
    fun `북마크_삽입시_정상삽입되어_MainUiState_BookmarkInsert_1_응답한다`() = runBlocking  {
        // given
        coEvery { naverRepositoryMock.insertBookmark(DummyData.bookmarkItem) }.returns(1)

        // when
        viewModel.insertBookmark(DummyData.bookmarkItem)

        // then
        verify(timeout = 1000L) { viewStateObserver.onChanged(MainUiState.InsertBookmark(1)) }
    }


    @Test
    fun `북마크_삽입시_삽입되지않아_MainUiState_BookmarkInsert_0_응답한다`() = runBlocking  {
        // given
        coEvery { naverRepositoryMock.insertBookmark(DummyData.bookmarkItem) }.returns(0)

        // when
        viewModel.insertBookmark(DummyData.bookmarkItem)

        // then
        verify(timeout = 1000L) { viewStateObserver.onChanged(MainUiState.InsertBookmark(0)) }
    }

    @Test
    fun `북마크_삭제시_정상삭제되어_MainUiState_BookmarkDelete_1_응답한다`() = runBlocking  {
        // given
        coEvery {
            naverRepositoryMock.deleteBookmark(
                DummyData.bookmarkItem.title,
                DummyData.bookmarkItem.image,
                DummyData.bookmarkItem.link
            )
        }.returns(1)

        // when
        viewModel.deleteBookmark(DummyData.bookmarkItem)

        // then
        verify(timeout = 1000L) { viewStateObserver.onChanged(MainUiState.DeleteBookmark(1)) }
    }

    @Test
    fun `북마크_삭제시_삭제실패하여_MainUiState_BookmarkDelete_0_응답한다`() = runBlocking  {
        // given
        coEvery {
            naverRepositoryMock.deleteBookmark(
                DummyData.bookmarkItem.title,
                DummyData.bookmarkItem.image,
                DummyData.bookmarkItem.link
            )
        }.returns(0)

        // when
        viewModel.deleteBookmark(DummyData.bookmarkItem)

        // then
        verify(timeout = 1000L) { viewStateObserver.onChanged(MainUiState.DeleteBookmark(0)) }
    }
}