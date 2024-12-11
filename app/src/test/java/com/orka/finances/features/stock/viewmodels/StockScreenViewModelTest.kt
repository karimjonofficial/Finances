package com.orka.finances.features.stock.viewmodels

import com.orka.finances.ID
import com.orka.finances.MainDispatcherContext
import com.orka.finances.STOCK_ITEM
import com.orka.finances.features.stock.presentation.viewmodels.StockScreenViewModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class StockScreenViewModelTest : MainDispatcherContext() {

    abstract class DummyStockDataSourceContext {
        val dataSource = DummyStockDataSource()
    }
    abstract class CalledContext {
        var called = false
    }

    @Test
    fun `When fetchData, get products`() {
        val dataSource = SpyStockDataSource()
        val viewModel = StockScreenViewModel(ID, dataSource, {}) {}
        viewModel.fetchData()
        assertTrue(dataSource.getCalled)
    }

    @Test
    fun `When data received, set state`() {
        val dataSource = StubStockDataSource()
        val viewModel = StockScreenViewModel(ID, dataSource, {}) {}
        viewModel.fetchData()
        assertEquals(1, viewModel.uiState.value.size)
    }

    @Test
    fun `When no data received, state is empty list`() {
        val dataSource = MockStockDataSource()
        val viewModel = StockScreenViewModel(ID, dataSource, {}) {}
        viewModel.fetchData()
        assertEquals(1, viewModel.uiState.value.size)
        viewModel.fetchData()
        assertTrue(viewModel.uiState.value.isEmpty())
    }

    @Nested
    inner class CalledContextImpl : CalledContext() {

        @Test
        fun `When fetchData throw unauthorized, unauthorize`() {
            val dataSource = ThrowingUnauthorizedStubStockDataSource()
            val viewModel = StockScreenViewModel(ID, dataSource, {}) { called = true }
            viewModel.fetchData()
            assertTrue(called)
        }

        @Test
        fun `When fetchData throw http, not unauthorize`() {
            val dataSource = ThrowingHttpStubStockDataSource()
            val viewModel = StockScreenViewModel(ID, dataSource, {}) { called = true }
            viewModel.fetchData()
            assertFalse(called)
        }
    }

    @Nested
    inner class DummySourceContext : DummyStockDataSourceContext() {

        @Test
        fun `When initialized, state is empty list`() {
            val viewModel = StockScreenViewModel(ID, dataSource, {}) {}
            assertTrue(viewModel.uiState.value.isEmpty())
        }

        @Test
        fun `When select item, navigate`() {
            var called = false
            val viewModel = StockScreenViewModel(ID, dataSource, { called = true }) {}
            viewModel.selectItem(STOCK_ITEM)
            assertTrue(called)
        }

        @Test
        fun `When select item, pass id`() {
            var id = 0
            val viewModel = StockScreenViewModel(ID, dataSource, { id = it }) {}
            viewModel.selectItem(STOCK_ITEM)
            assertEquals(id, STOCK_ITEM.id)
        }
    }
}