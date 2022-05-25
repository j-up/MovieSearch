package com.jm.moviesearch.data.local

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.jm.moviesearch.base.BaseTest
import com.jm.moviesearch.data.NaverEntity
import com.jm.moviesearch.data.local.dao.BookmarkDao
import com.jm.moviesearch.source.LocalNaverDataSource
import com.jm.moviesearch.util.DummyData
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class LocalNaverDataSourceTest: BaseTest() {

    @MockK
    lateinit var bookmarkDao: BookmarkDao

    lateinit var localNaverDataSource: LocalNaverDataSource

    override fun setup() {
        super.setup()
        bookmarkDao = mockk()
        localNaverDataSource = LocalNaverDataSource(bookmarkDao)
    }

    @Test
    fun `getNaverMovie_호출시_지원하지않아_UnsupportedOperationException_발생한다`() = runBlocking {
        // given
        var isUnsupportedOperationException:Boolean = false

        // when
        try {
            localNaverDataSource.getNaverMovie("")
        } catch (e: UnsupportedOperationException) {
            isUnsupportedOperationException = true
        }

        // then
        Assert.assertEquals(true, isUnsupportedOperationException)
    }


    @Test
    fun `북마크리스트_구독후_값_갱신시_정상적으로_조회하여_리스트_반환된다`() = runBlocking {
        //given
        var bookmarkListLiveData: MutableLiveData<List<NaverEntity.Bookmark>> = MutableLiveData()
        var bookmarkListObserver: Observer<List<NaverEntity.Bookmark>> = mockk()

        coEvery { bookmarkDao.getAllBookmarkLiveData() }.returns(MutableLiveData(DummyData.bookmarkList))
        every { bookmarkListObserver.onChanged(any()) }.answers {  }
        bookmarkListLiveData.observeForever(bookmarkListObserver)

        // when
        bookmarkListLiveData.value = localNaverDataSource.getAllBookmarkLiveData().value

        // then
        verify(timeout = 1000L) { bookmarkListObserver.onChanged(DummyData.bookmarkList) }
    }

    @Test
    fun `북마크리스트_구독후_값_갱신시_비어있는_리스트_조회하여_비어있는_리스트가_반환된다`() = runBlocking {
        //given
        var bookmarkListLiveData: MutableLiveData<List<NaverEntity.Bookmark>> = MutableLiveData()
        var bookmarkListObserver: Observer<List<NaverEntity.Bookmark>> = mockk()

        coEvery { bookmarkDao.getAllBookmarkLiveData() }.returns(MutableLiveData(listOf()))
        every { bookmarkListObserver.onChanged(any()) }.answers {  }
        bookmarkListLiveData.observeForever(bookmarkListObserver)

        // when
        bookmarkListLiveData.value = localNaverDataSource.getAllBookmarkLiveData().value

        // then
        verify(timeout = 1000L) { bookmarkListObserver.onChanged(listOf()) }
    }

    @Test
    fun `getAllBookmark_호출시_정상적으로_조회하여_리스트_반환된다`() = runBlocking {
        //given
        coEvery { bookmarkDao.getAllBookmark() }.returns(DummyData.bookmarkList)

        // when
        val result: List<NaverEntity.Bookmark> = localNaverDataSource.getAllBookmark()

        // then
        Assert.assertEquals(DummyData.bookmarkList, result)
    }

    @Test
    fun `getAllBookmark_호출시_정상적으로_조회하여_빈_리스트_반환된다`() = runBlocking {
        //given
        coEvery { bookmarkDao.getAllBookmark() }.returns(emptyList())

        // when
        val result: List<NaverEntity.Bookmark> = localNaverDataSource.getAllBookmark()

        // then
        Assert.assertEquals(emptyList<NaverEntity.Bookmark>(), result)
    }

    @Test
    fun `insertBookmark_호출시_정상적으로_삽입되어_1_반환된다`() = runBlocking {
        //given
        coEvery { bookmarkDao.insertBookmark(DummyData.bookmarkItem) }.returns(1)

        // when
        val result: Long = localNaverDataSource.insertBookmark(DummyData.bookmarkItem)

        // then
        Assert.assertEquals(1, result)
    }

    @Test
    fun `insertBookmark_호출시_삽입실패하여_0_반환된다`() = runBlocking {
        //given
        coEvery { bookmarkDao.insertBookmark(DummyData.bookmarkItem) }.returns(0)

        // when
        val result: Long = localNaverDataSource.insertBookmark(DummyData.bookmarkItem)

        // then
        Assert.assertEquals(0, result)
    }

    @Test
    fun `deleteBookmark_호출시_정상적으로_삭제되어_1_반환된다`() = runBlocking {
        //given
        coEvery {
            bookmarkDao.deleteBookmark(
                DummyData.bookmarkItem.title,
                DummyData.bookmarkItem.image,
                DummyData.bookmarkItem.link
            )
        }.returns(1)

        // when
        val result: Int = localNaverDataSource.deleteBookmark(
            DummyData.bookmarkItem.title,
            DummyData.bookmarkItem.image,
            DummyData.bookmarkItem.link
        )

        // then
        Assert.assertEquals(1, result)
    }

    @Test
    fun `deleteBookmark_호출시_삭제실패하여_0_반환된다`() = runBlocking {
        //given
        coEvery {
            bookmarkDao.deleteBookmark(
                DummyData.bookmarkItem.title,
                DummyData.bookmarkItem.image,
                DummyData.bookmarkItem.link
            )
        }.returns(0)

        // when
        val result: Int = localNaverDataSource.deleteBookmark(
            DummyData.bookmarkItem.title,
            DummyData.bookmarkItem.image,
            DummyData.bookmarkItem.link
        )

        // then
        Assert.assertEquals(0, result)
    }
}