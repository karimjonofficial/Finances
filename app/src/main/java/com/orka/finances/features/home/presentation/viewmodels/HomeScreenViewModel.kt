package com.orka.finances.features.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.models.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val dataSource: CategoriesDataSource,
    private val passScreen: (Int) -> Unit
) : ViewModel() {
    private val _categories = MutableStateFlow(emptyList<Category>())
    val categories: StateFlow<List<Category>> = _categories

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _categories.value = dataSource.get() ?: emptyList()
        }
    }

    fun selectCategory(category: Category) {
        passScreen(category.id)
    }
}