package com.orka.finances.features.home.viewmodels

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

    abstract class DummyCredentialsDataSourceContext {
        val credentialsDataSource = DummyCredentialsDataSource()

        abstract class WithCounter {
            val counter = Counter()
        }
    }

    @Test
    fun callsGetCredentialsWhenFetching() {
        val credentialsDataSource = SpyCredentialsDataSource()
        val dataSource = StubCategoriesDataSourceWithNoData()
        val count = credentialsDataSource.counter.count
        val viewModel = HomeScreenViewModel(dataSource, credentialsDataSource, {}) {}
        viewModel.fetchData()
        assertEquals(count + 1, credentialsDataSource.counter.count)
    }

    @Nested
    inner class DummyCredentialsDataSourceContextImpl : DummyCredentialsDataSourceContext() {

        @Test
        fun setsUiEmptyWhenNoDataFetched() = runTest {
            val dataSource = StubCategoriesDataSourceWithNoData()
            val viewModel = HomeScreenViewModel(dataSource, credentialsDataSource, {}){}
            assertEquals(0, viewModel.uiState.value.size)
        }

        @Nested
        inner class WithCounter : DummyCredentialsDataSourceContext.WithCounter() {

            @Test
            fun callsPassScreenWhenSelectedCategory() = runTest {
                val dataSource = StubCategoriesDataSourceWithNoData()
                val viewModel = HomeScreenViewModel(dataSource, credentialsDataSource, { counter.count() }) {}
                val category = Category(ID, NAME, DESCRIPTION)
                val count = counter.count

                viewModel.selectCategory(category)

                assertEquals(count + 1, counter.count)
            }

            @Test
            fun callsUnauthorizeWhenUserNotAuthorized() = runTest {
                val dataSource = ThrowingStubCategoriesDataSource()
                val viewModel = HomeScreenViewModel(dataSource, credentialsDataSource, {}) { counter.count() }
                val count = counter.count

                viewModel.fetchData()

                assertEquals(count + 1, counter.count)
            }
        }
    }
}