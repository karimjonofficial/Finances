package com.orka.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orka.base.Product
import com.orka.base.ProductsDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductsScreenViewModel(private val dataSource: ProductsDataSource) : ViewModel() {
    private val _uiState = MutableStateFlow(emptyList<Product>())
    val uiState: StateFlow<List<Product>> = _uiState.asStateFlow()

    fun fetch() {
        viewModelScope.launch {
            _uiState.value = dataSource.get() ?: emptyList()
        }
    }
}

