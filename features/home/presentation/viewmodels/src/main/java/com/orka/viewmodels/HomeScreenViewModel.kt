package com.orka.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orka.base.Category
import com.orka.lib.resources.HttpStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeScreenViewModel(
    private val dataSource: com.orka.base.CategoriesDataSource,
    private val navigate: (Int) -> Unit,
    private val unauthorize: () -> Unit
) : ViewModel() {
    private val _uiState = MutableStateFlow(emptyList<Category>())
    val uiState: StateFlow<List<Category>> = _uiState

    fun fetchData() {
        viewModelScope.launch {
            try {
                _uiState.value = dataSource.get() ?: emptyList()
            } catch (e: HttpException) {
                if (e.code() == HttpStatus.Unauthorized.code) {
                    unauthorize()
                }
            }
        }
    }

    fun selectCategory(category: Category) {
        navigate(category.id)
    }

    fun addCategory(name: String, description: String) {
        if (name.isNotBlank()) {
            viewModelScope.launch {
                try {
                    if(dataSource.add(name, description) != null)
                        fetchData()
                } catch (e: HttpException) {
                    if(e.code() == HttpStatus.Unauthorized.code) {
                        unauthorize()
                    }
                }
            }
        }
    }
}