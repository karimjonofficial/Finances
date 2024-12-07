package com.orka.finances.features.home.viewmodels

import com.orka.finances.BLANK_LINE
import com.orka.finances.Counter
import com.orka.finances.DESCRIPTION
import com.orka.finances.ID
import com.orka.finances.MainDispatcherContext
import com.orka.finances.NAME
import com.orka.finances.features.home.models.Category
import com.orka.finances.features.home.presentation.viewmodels.HomeScreenViewModel
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class HomeScreenViewModelTest : MainDispatcherContext() {

    abstract class StubCategoriesDataSourceWithDataContext {
        val dataSource = StubCategoriesDataSourceWithData()
    }
    abstract class CounterContext {
        val counter = Counter()

        abstract class WithThrowingUnauthorizedDataSource : CounterContext() {
            private val dataSource = ThrowingUnauthorizedStubCategoriesDataSource()
            val viewModel = HomeScreenViewModel(dataSource, {}) { counter.count() }
            val count = counter.count
        }

        abstract class WithThrowingHttpExceptionDataSource : CounterContext() {
            private val dataSource = ThrowingHttpExceptionStubCategoriesDataSource()
            val viewModel = HomeScreenViewModel(dataSource, {}) { counter.count() }
            val count = counter.count
        }
    }
    abstract class WithSpyCategoriesDataSourceForAdd {
        val dataSource = SpyCategoriesDataSourceForAdd()
        val count = dataSource.counter.count
        val viewModel = HomeScreenViewModel(dataSource, {}, {})
    }

    @Test
    fun setsUiEmptyWhenNoDataFetched() = runTest {
        val dataSource = StubCategoriesDataSourceWithNoData()
        val viewModel = HomeScreenViewModel(dataSource, {}) {}
        viewModel.fetchData()
        assertEquals(0, viewModel.uiState.value.size)
    }

    @Test
    fun `When addCategory return result, call fetchData`() {
        val dataSource = StubCategoriesDataSourceReturnsResult()
        val viewModel = HomeScreenViewModel(dataSource, {}) {}
        val initSize = viewModel.uiState.value.size
        viewModel.addCategory(NAME, DESCRIPTION)
        assertEquals(0, initSize)
        assertEquals(1, viewModel.uiState.value.size)
    }

    @Test
    fun `When addCategory return null, no call fetchData`() {
        val dataSource = StubCategoriesDataSourceReturnNull()
        val viewModel = HomeScreenViewModel(dataSource, {}) {}
        val initSize = viewModel.uiState.value.size
        viewModel.addCategory(NAME, DESCRIPTION)
        assertEquals(0, initSize)
        assertEquals(0, viewModel.uiState.value.size)
    }

    @Nested
    inner class WithStubDataSourceWithData : StubCategoriesDataSourceWithDataContext() {

        @Test
        fun setsUiWhenDataFetched() = runTest {
            val viewModel = HomeScreenViewModel(dataSource, {}) {}
            viewModel.fetchData()
            assertEquals(1, viewModel.uiState.value.size)
        }
    }

    @Nested
    inner class CounterContextImpl : CounterContext() {

        @Test
        fun `When select a category, pass screen`() = runTest {
            val dataSource = StubCategoriesDataSourceWithNoData()
            val viewModel = HomeScreenViewModel(dataSource, { counter.count() }) {}
            val category = Category(ID, NAME, DESCRIPTION)
            val count = counter.count

            viewModel.selectCategory(category)

            assertEquals(count + 1, counter.count)
        }

        @Nested
        inner class WithThrowingUnauthorized : WithThrowingUnauthorizedDataSource() {

            @Test
            fun `When fetchData unauthorized thrown, unauthorize`() = runTest {
                viewModel.fetchData()
                assertEquals(count + 1, counter.count)
            }

            @Test
            fun `When addCategory unauthorized thrown, unauthorize`() {
                viewModel.addCategory(NAME, DESCRIPTION)
                assertEquals(count + 1, counter.count)
            }
        }

        @Nested
        inner class WithThrowingHttpException : WithThrowingHttpExceptionDataSource() {

            @Test
            fun `When fetchData http thrown, don't unauthorize`() = runTest {
                viewModel.fetchData()
                assertEquals(count, counter.count)
            }

            @Test
            fun `When addCategory http thrown, don't unauthorize`() {
                viewModel.addCategory(NAME, DESCRIPTION)
                assertEquals(count, counter.count)
            }
        }
    }

    @Nested
    inner class WithSpyDataSourceForAdd : WithSpyCategoriesDataSourceForAdd() {

        @Test
        fun `When name is blank, don't add`() {
            viewModel.addCategory(BLANK_LINE, DESCRIPTION)
            assertEquals(count, dataSource.counter.count)
        }

        @Test
        fun `When name is not blank, add`() {
            viewModel.addCategory(NAME, DESCRIPTION)
            assertEquals(count + 1, dataSource.counter.count)
        }
    }
}