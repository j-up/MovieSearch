package com.jm.moviesearch.ui.detail

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

class DetailViewModelTest: BaseViewModelTest<DetailViewModel, DetailUiState>() {

    @MockK
    lateinit var naverRepositoryMock: NaverRepository

    @Before
    override fun setup() {
        super.setup()
        naverRepositoryMock = mockk()
        viewStateObserver = mockk(relaxed = true)

        // 기본적으로 비어있는 북마크 리스트 반환
        every { naverRepositoryMock.getAllBookmarkLiveData().value }.returns(emptyList())

        viewModel = DetailViewModel(naverRepositoryMock)
        viewModel.viewStateLiveData.observeForever(viewStateObserver)
    }

    @Test
    fun `북마크_삭제_클릭시_북마크_값이_True_일때_DeleteBookmark_호출하여_DetailUiState_BookmarkDelete_1_응답한다`() {
        // given
        coEvery {
            naverRepositoryMock.deleteBookmark(
                DummyData.movieItemBookmarkIsTrue.title,
                DummyData.movieItemBookmarkIsTrue.image,
                DummyData.movieItemBookmarkIsTrue.link
            )
        }.returns(1)

        // when
        viewModel.onBookmarkClick(DummyData.movieItemBookmarkIsTrue)

        // then
        verify(timeout = 1000L) { viewStateObserver.onChanged(DetailUiState.DeleteBookmark(1)) }
    }

    @Test
    fun `북마크_추가_클릭시_북마크_값이_False_일때_InsertBookmark_호출하여_DetailUiState_BookmarkInsert_1_응답한다`() {
        // given
        coEvery { naverRepositoryMock.insertBookmark(DummyData.movieItemBookmarkIsFalse.toBookmark()) }.returns(1)

        // when
        viewModel.onBookmarkClick(DummyData.movieItemBookmarkIsFalse)

        // then
        verify(timeout = 1000L) { viewStateObserver.onChanged(DetailUiState.InsertBookmark(1)) }
    }

    @Test
    fun `북마크_삽입시_정상삽입되어_DetailUiState_BookmarkInsert_1_응답한다`() = runBlocking  {
        // given
        coEvery { naverRepositoryMock.insertBookmark(DummyData.bookmarkItem) }.returns(1)

        // when
        viewModel.insertBookmark(DummyData.bookmarkItem)

        // then
        verify(timeout = 1000L) { viewStateObserver.onChanged(DetailUiState.InsertBookmark(1)) }
    }


    @Test
    fun `북마크_삽입시_삽입되지않아_DetailUiState_BookmarkInsert_0_응답한다`() = runBlocking  {
        // given
        coEvery { naverRepositoryMock.insertBookmark(DummyData.bookmarkItem) }.returns(0)

        // when
        viewModel.insertBookmark(DummyData.bookmarkItem)

        // then
        verify(timeout = 1000L) { viewStateObserver.onChanged(DetailUiState.InsertBookmark(0)) }
    }


    @Test
    fun `북마크_삭제시_정상삭제되어_DetailUiState_BookmarkDelete_1_응답한다`() = runBlocking  {
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
        verify(timeout = 1000L) { viewStateObserver.onChanged(DetailUiState.DeleteBookmark(1)) }
    }

    @Test
    fun `북마크_삭제시_삭제실패하여_DetailUiState_BookmarkDelete_0_응답한다`() = runBlocking  {
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
        verify(timeout = 1000L) { viewStateObserver.onChanged(DetailUiState.DeleteBookmark(0)) }
    }


    @Test
    fun `북마크_삭제시_Exception_발생하여_coroutineExceptionHandler_DetailUiState_Error_응답한다`() = runBlocking  {
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
        verify(timeout = 1000L) { viewStateObserver.onChanged(DetailUiState.Error("delete fail")) }
    }

    @Test
    fun `닫기버튼_클릭시_DetailUiState_Finish_응답한다`() = runBlocking {
        // when
        viewModel.onCloseClick()

        // then
        verify(timeout = 1000L) { viewStateObserver.onChanged(DetailUiState.Finish()) }
    }
}