package com.orka.viewmodels

import com.orka.lib.Counter
import com.orka.lib.MainDispatcherContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ProductsScreenViewModelTest : MainDispatcherContext() {

    @Test
    fun `When initialized, has an empty state`() {
        val dataSource = DummyProductsDataSource()
        val viewModel = ProductsScreenViewModel(dataSource) {}
        assertTrue(viewModel.uiState.value.isEmpty())
    }

    @Test
    fun `When fetch, call get`() {
        val dataSource = SpyProductsDataSource()
        val viewModel = ProductsScreenViewModel(dataSource) {}
        viewModel.fetch()
        assertTrue(dataSource.getCalled)
    }

    @Test
    fun `When fetch gets null, sets state empty`() {
        val dataSource = StubProductsDataSourceWithNoData()
        val viewModel = ProductsScreenViewModel(dataSource) {}
        viewModel.fetch()
        assertTrue(viewModel.uiState.value.isEmpty())
    }

    @Test
    fun `When fetch, set state`() {
        val dataSource = StubProductsDataSourceWithData()
        val viewModel = ProductsScreenViewModel(dataSource) {}
        viewModel.fetch()
        assertFalse(viewModel.uiState.value.isEmpty())
    }

    @Test
    fun `When fetch unauthorized thrown, unauthorize`() {
        val counter = Counter()
        val count = counter.count
        val dataSource = ThrowingUnauthorizedProductsDataSource()
        val viewModel = ProductsScreenViewModel(dataSource) { counter.count() }
        viewModel.fetch()
        assertEquals(count + 1, counter.count)
    }

    @Test
    fun `When fetch http thrown, not unauthorize`() {
        val counter = Counter()
        val count = counter.count
        val dataSource = ThrowingHttpProductsDataSource()
        val viewModel = ProductsScreenViewModel(dataSource) { counter.count() }
        viewModel.fetch()
        assertEquals(count, counter.count)
    }
}