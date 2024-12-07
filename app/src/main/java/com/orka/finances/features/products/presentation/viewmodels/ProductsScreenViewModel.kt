package com.orka.finances.features.products.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.orka.finances.features.products.data.sources.ProductsDataSource
import com.orka.finances.features.products.models.Product
import com.orka.finances.lib.data.credentials.CredentialsDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductsScreenViewModel(
    private val categoryId: Int,
    private val dataSource: ProductsDataSource,
    private val credentialsDataSource: CredentialsDataSource
) : ViewModel() {
    private val _uiState = MutableStateFlow(emptyList<Product>())
    val uiState = _uiState.asStateFlow()

    fun fetchData() {
        credentialsDataSource.get()
        dataSource.get(categoryId)
    }
}