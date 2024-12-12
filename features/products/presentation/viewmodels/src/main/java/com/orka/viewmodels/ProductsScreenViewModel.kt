package com.orka.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orka.lib.resources.HttpStatus
import com.orka.base.Product
import com.orka.base.ProductsDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ProductsScreenViewModel(
    private val dataSource: ProductsDataSource,
    private val unauthorize: () -> Unit
) : ViewModel() {
    private val _uiState = MutableStateFlow(emptyList<Product>())
    val uiState: StateFlow<List<Product>> = _uiState.asStateFlow()

    fun fetch() {
        viewModelScope.launch {
            try {
                _uiState.value = dataSource.get() ?: emptyList()
            } catch(e: Exception) {
                if(e is HttpException && e.code() == HttpStatus.Unauthorized.code)
                    unauthorize()
            }
        }
    }
}

