package com.orka.finances.features.products.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.orka.finances.features.products.data.sources.ProductsDataSource
import com.orka.finances.features.products.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductsScreenViewModel(
    private val categoryId: Int,
    private val dataSource: ProductsDataSource,
) : ViewModel() {

    private val _uiState = MutableStateFlow(emptyList<Product>())
    val uiState = _uiState.asStateFlow()

    init {
        val products = dataSource.get(categoryId)
        products?.let { _uiState.value = it }
    }
}