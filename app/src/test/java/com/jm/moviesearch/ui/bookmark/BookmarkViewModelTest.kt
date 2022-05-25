package com.jm.moviesearch.ui.bookmark

import com.jm.moviesearch.base.BaseViewModelTest
import com.jm.moviesearch.util.DummyData
import com.jm.moviesearch.repository.NaverRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.io.IOException

class BookmarkViewModelTest: BaseViewModelTest<BookmarkViewModel, BookmarkUiState>() {

    @MockK
    lateinit var naverRepositoryMock:NaverRepository

    @Before
    override fun setup() {
        super.setup()
        naverRepositoryMock = mockk()
        viewStateObserver = mockk(relaxed = true)

        // 기본적으로 비어있는 북마크 리스트 반환
        every { naverRepositoryMock.getAllBookmarkLiveData().value }.returns(emptyList())

        viewModel = BookmarkViewModel(naverRepositoryMock)
        viewModel.viewStateLiveData.observeForever(viewStateObserver)
    }


    @Test
    fun `북마크_삭제시_정상삭제되어_BookmarkUiState_BookmarkDelete_1_응답한다`() = runBlocking  {
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
        verify(timeout = 1000L) { viewStateObserver.onChanged(BookmarkUiState.DeleteBookmark(1)) }
    }

    @Test
    fun `북마크_삭제시_삭제실패하여_BookmarkUiState_BookmarkDelete_0_응답한다`() = runBlocking  {
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
        verify(timeout = 1000L) { viewStateObserver.onChanged(BookmarkUiState.DeleteBookmark(0)) }
    }

    @Test
    fun `북마크_삭제시_Exception_발생하여_coroutineExceptionHandler_BookmarkUiState_Error_응답한다`() = runBlocking  {
        // given
        coEvery {
            naverRepositoryMock.deleteBookmark(
                DummyData.bookmarkItem.title,
                DummyData.bookmarkItem.image,
                DummyData.bookmarkItem.link
            )
        }.throws(IOException("delete fail"))

        // when
        viewModel.deleteBookmark(DummyData.bookmarkItem)

        // then
        verify(timeout = 1000L) { viewStateObserver.onChanged(BookmarkUiState.Error("delete fail")) }
    }

    @Test
    fun `닫기버튼_클릭시_BookmarkUiState_Finish_응답한다`() = runBlocking {
        // when
        viewModel.onCloseClick()

        // then
        verify(timeout = 1000L) { viewStateObserver.onChanged(BookmarkUiState.Finish()) }
    }

}