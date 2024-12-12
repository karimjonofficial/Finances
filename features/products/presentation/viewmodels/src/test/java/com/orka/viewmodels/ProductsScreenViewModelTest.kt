package com.orka.viewmodels

import com.orka.lib.BLANK_LINE
import com.orka.lib.Counter
import com.orka.lib.IMG_SRC
import com.orka.lib.MainDispatcherContext
import com.orka.lib.NAME
import com.orka.lib.PRICE
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ProductsScreenViewModelTest : MainDispatcherContext() {

    abstract class SpyDataSourceContext {
        val dataSource = SpyProductsDataSource()
        val viewModel = ProductsScreenViewModel(dataSource) {}
    }

    @Test
    fun `When initialized, has an empty state`() {
        val dataSource = DummyProductsDataSource()
        val viewModel = ProductsScreenViewModel(dataSource) {}
        assertTrue(viewModel.uiState.value.isEmpty())
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

    @Nested
    inner class SpySourceContext : SpyDataSourceContext() {

        @Test
        fun `When fetch, call get`() {
            viewModel.fetch()
            assertTrue(dataSource.getCalled)
        }

        @Test
        fun `When name is blank, not add`() {
            viewModel.add(name = BLANK_LINE, price = PRICE, imgSrc = IMG_SRC)
            assertFalse(dataSource.addCalled)
        }

        @Test
        fun `When price is less than zero or equals, not add`() {
            viewModel.add(name = NAME, price = 0.0, imgSrc = IMG_SRC)
            assertFalse(dataSource.addCalled)
        }

        @Test
        fun `When add success, call fetch`() {
            viewModel.add(NAME, PRICE, IMG_SRC)
            assertTrue(dataSource.getCalled)
        }
    }
}