package com.orka.finances.features.home.viewmodels

import com.orka.finances.BLANK_LINE
import com.orka.finances.Counter
import com.orka.finances.DESCRIPTION
import com.orka.finances.ID
import com.orka.finances.MainDispatcherContext
import com.orka.finances.NAME
import com.orka.finances.features.DummyCredentialsDataSource
import com.orka.finances.features.SpyCredentialsDataSource
import com.orka.finances.features.home.models.Category
import com.orka.finances.features.home.presentation.viewmodels.HomeScreenViewModel
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class HomeScreenViewModelTest : MainDispatcherContext() {

    abstract class DummyCredentialsDataSourceContext {
        val credentialsDataSource = DummyCredentialsDataSource()

        abstract class WithStubCategoriesDataSourceWithData {
            val dataSource = StubCategoriesDataSourceWithData()
        }

        abstract class WithCounter : DummyCredentialsDataSourceContext() {
            val counter = Counter()

            abstract class WithThrowingUnauthorizedDataSource : WithCounter() {
                private val dataSource = ThrowingUnauthorizedStubCategoriesDataSource()
                val viewModel =
                    HomeScreenViewModel(dataSource, credentialsDataSource, {}) { counter.count() }
                val count = counter.count
            }

            abstract class WithThrowingHttpExceptionDataSource : WithCounter() {
                private val dataSource = ThrowingHttpExceptionStubCategoriesDataSource()
                val viewModel =
                    HomeScreenViewModel(dataSource, credentialsDataSource, {}) { counter.count() }
                val count = counter.count
            }
        }

        abstract class WithSpyCategoriesDataSourceForAdd : DummyCredentialsDataSourceContext() {
            val dataSource = SpyCategoriesDataSourceForAdd()
            val count = dataSource.counter.count
            val viewModel = HomeScreenViewModel(dataSource, credentialsDataSource, {}, {})
        }
    }
    abstract class SpyCredentialsDataSourceContext {
        val credentialsDataSource = SpyCredentialsDataSource()
        val count = credentialsDataSource.counter.count
    }

    @Nested
    inner class SpyCredentialsDataSourceContextImpl : SpyCredentialsDataSourceContext() {

        @Test
        fun callsGetCredentialsWhenFetching() {
            val dataSource = StubCategoriesDataSourceWithNoData()
            val viewModel = HomeScreenViewModel(dataSource, credentialsDataSource, {}) {}
            viewModel.fetchData()
            assertEquals(count + 1, credentialsDataSource.counter.count)
        }

        @Test
        fun `Get credentials when adding new category`() {
            val dataSource = DummyCategoriesDataSource()
            val viewModel = HomeScreenViewModel(dataSource, credentialsDataSource, {}, {})
            viewModel.addCategory(NAME, DESCRIPTION)

            assertEquals(count + 1, credentialsDataSource.counter.count)
        }
    }

    @Nested
    inner class DummyCredentialsDataSourceContextImpl : DummyCredentialsDataSourceContext() {

        @Test
        fun setsUiEmptyWhenNoDataFetched() = runTest {
            val dataSource = StubCategoriesDataSourceWithNoData()
            val viewModel = HomeScreenViewModel(dataSource, credentialsDataSource, {}) {}
            viewModel.fetchData()
            assertEquals(0, viewModel.uiState.value.size)
        }

        @Test
        fun `When addCategory return result, call fetchData`() {
            val dataSource = StubCategoriesDataSourceReturnsResult()
            val viewModel = HomeScreenViewModel(dataSource, credentialsDataSource, {}) {}
            val initSize = viewModel.uiState.value.size
            viewModel.addCategory(NAME, DESCRIPTION)
            assertEquals(0, initSize)
            assertEquals(1, viewModel.uiState.value.size)
        }

        @Test
        fun `When addCategory return null, no call fetchData`() {
            val dataSource = StubCategoriesDataSourceReturnNull()
            val viewModel = HomeScreenViewModel(dataSource, credentialsDataSource, {}) {}
            val initSize = viewModel.uiState.value.size
            viewModel.addCategory(NAME, DESCRIPTION)
            assertEquals(0, initSize)
            assertEquals(0, viewModel.uiState.value.size)
        }

        @Nested
        inner class WithStubDataSourceWithData : WithStubCategoriesDataSourceWithData() {

            @Test
            fun setsUiWhenDataFetched() = runTest {
                val viewModel = HomeScreenViewModel(dataSource, credentialsDataSource, {}) {}
                viewModel.fetchData()
                assertEquals(1, viewModel.uiState.value.size)
            }
        }

        @Nested
        inner class WithCounter : DummyCredentialsDataSourceContext.WithCounter() {

            @Test
            fun `When select a category, pass screen`() = runTest {
                val dataSource = StubCategoriesDataSourceWithNoData()
                val viewModel =
                    HomeScreenViewModel(dataSource, credentialsDataSource, { counter.count() }) {}
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
}