package com.orka.finances.features.stock.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orka.finances.features.stock.data.sources.StockDataSource
import com.orka.finances.features.stock.models.StockItem
import com.orka.finances.lib.log.Log
import com.orka.finances.lib.resources.HttpStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class StockScreenViewModel(
    private val categoryId: Int,
    private val dataSource: StockDataSource,
    private val navigate: (Int) -> Unit,
    private val unauthorize: () -> Unit
) : ViewModel() {
    private val _uiState = MutableStateFlow(emptyList<StockItem>())
    val uiState = _uiState.asStateFlow()

    fun fetchData() {
        viewModelScope.launch {
            try {
                _uiState.value = dataSource.get(categoryId) ?: emptyList()
                Log("StockScreenViewModel.Data.Success", _uiState.value.size.toString())
            } catch(e: HttpException) {
                if(e.code() == HttpStatus.Unauthorized.code) {
                    unauthorize()
                }
                Log("StockScreenViewModel.Data.Exception", e.message.toString())
            }
        }
    }

    fun selectItem(item: StockItem) {
        navigate(item.id)
    }
}