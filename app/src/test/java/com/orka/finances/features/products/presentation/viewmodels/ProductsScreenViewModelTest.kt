package com.orka.finances.features.products.presentation.viewmodels

import com.orka.finances.ID
import com.orka.finances.MainDispatcherContext
import com.orka.finances.features.DummyCredentialsDataSource
import com.orka.finances.features.SpyCredentialsDataSource
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ProductsScreenViewModelTest : MainDispatcherContext() {

    @Test
    fun `When initialized, state is empty list`() {
        val credentialsDataSource = DummyCredentialsDataSource()
        val dataSource = DummyProductsDataSource()
        val viewModel = ProductsScreenViewModel(ID, dataSource, credentialsDataSource)
        assertTrue(viewModel.uiState.value.isEmpty())
    }

    @Test
    fun `When fetchData, get credentials`() {
        val credentialsDataSource = SpyCredentialsDataSource()
        val count = credentialsDataSource.counter.count
        val dataSource = DummyProductsDataSource()
        val viewModel = ProductsScreenViewModel(ID, dataSource, credentialsDataSource)
        viewModel.fetchData()
        assertEquals(count + 1, credentialsDataSource.counter.count)
    }

    @Test
    fun `When fetchData, get products`() {
        val credentialsDataSource = DummyCredentialsDataSource()
        val dataSource = SpyProductsDataSource()
        val viewModel = ProductsScreenViewModel(ID, dataSource, credentialsDataSource)
        viewModel.fetchData()
        assertTrue(dataSource.getCalled)
    }
}