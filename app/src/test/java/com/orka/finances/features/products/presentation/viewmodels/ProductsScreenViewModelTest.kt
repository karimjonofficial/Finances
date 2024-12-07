package com.orka.finances.features.products.presentation.viewmodels

import com.orka.finances.ID
import com.orka.finances.MainDispatcherContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ProductsScreenViewModelTest : MainDispatcherContext() {

    @Test
    fun `When initialized, state is empty list`() {
        val dataSource = DummyProductsDataSource()
        val viewModel = ProductsScreenViewModel(ID, dataSource)
        assertTrue(viewModel.uiState.value.isEmpty())
    }

    @Test
    fun `When fetchData, get products`() {
        val dataSource = SpyProductsDataSource()
        val viewModel = ProductsScreenViewModel(ID, dataSource)
        viewModel.fetchData()
        assertTrue(dataSource.getCalled)
    }
}