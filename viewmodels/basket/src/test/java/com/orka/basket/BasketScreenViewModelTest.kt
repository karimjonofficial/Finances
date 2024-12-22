package com.orka.basket

import com.orka.basket.fixtures.DummyHttpService
import com.orka.basket.fixtures.SpyBasketDataSource
import com.orka.basket.fixtures.StubBasketDataSource
import com.orka.core.HttpService
import com.orka.products.Product
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class BasketScreenViewModelTest {

    private val httpService: HttpService = DummyHttpService()

    @Nested
    inner class SpyBasketDataSourceContext {

        private val dataSource = SpyBasketDataSource()
        private val viewModel = BasketScreenViewModel(httpService, dataSource)

        @Nested
        inner class CallClearContext {

            init { viewModel.clear() }

            @Test
            fun `When cleaning call clean from data source`() {
                assertTrue(dataSource.clearCalled)
            }

            @Test
            fun `When cleaning call get from data source`() {
                assertTrue(dataSource.getCalled)
            }
        }

        @Nested
        inner class BasketItemContext {

            private val product = Product(1, "Product1", 1000.0, "", 1)

            @Test
            fun `When increasing an item call increase`() {
                viewModel.increase(product.id)
                assertTrue(dataSource.increaseCalled)
            }

            @Test
            fun `When increasing an item add only one item`() {
                viewModel.increase(product.id)
                assertEquals(1, dataSource.increaseAmount)
            }

            @Test
            fun `When decreasing an item call decrease`() {
                viewModel.decrease(product.id)
                assertTrue(dataSource.decreaseCalled)
            }

            @Test
            fun `When decreasing an item add only one item`() {
                viewModel.decrease(product.id)
                assertEquals(1, dataSource.decreaseAmount)
            }

            @Test
            fun `When removing item from basket calls remove from data source`() {
                viewModel.remove(product.id)
                assertTrue(dataSource.removeCalled)
            }
        }
    }

    @Nested
    inner class StubBasketDataSourceContext {

        private val id = 1
        private val dataSource = StubBasketDataSource()
        private val viewModel = BasketScreenViewModel(httpService, dataSource)

        @Test
        fun `When cleaning change the state`() {
            viewModel.clear()
            assertTrue(viewModel.uiState.value.basket.items.isNotEmpty())
        }

        @Test
        fun `When increasing an item changes state`() {
            viewModel.increase(id)
            assertTrue(viewModel.uiState.value.basket.items.isNotEmpty())
        }

        @Test
        fun `When decreasing an item changes state`() {
            viewModel.decrease(id)
            assertTrue(viewModel.uiState.value.basket.items.isNotEmpty())
        }

        @Test
        fun `When removing item from basket changes state`() {
            viewModel.remove(id)
            assertTrue(viewModel.uiState.value.basket.items.isNotEmpty())
        }

    }
}