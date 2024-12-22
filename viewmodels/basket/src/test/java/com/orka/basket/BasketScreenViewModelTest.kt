package com.orka.basket

import com.orka.core.HttpService
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BasketScreenViewModelTest {

    private val basket = Basket()
    private val httpService: HttpService = DummyHttpService()
    private val dataSource = SpyBasketDataSource()
    private val viewModel = BasketScreenViewModel(basket, httpService, dataSource)

    init { viewModel.clear() }

    @Test
    fun `When cleaning call clean from data source`() {
        assertTrue(dataSource.clearCalled)
    }

    @Test
    fun `When cleaning call get from data source`() {
        assertTrue(dataSource.getCalled)
    }

    @Test
    fun `When cleaning change the state`() {
        assertTrue(dataSource.basket === viewModel.uiState.value)
    }
}