package com.orka.finances.features.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.lib.errors.data.sources.NullDataSourceError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeScreenViewModel(private val dataSource: CategoriesDataSource) : ViewModel() {
    private val _categories: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val categories: StateFlow<List<String>> = _categories

    init {
        val pair = dataSource.getCategories()
        if(pair.second == NullDataSourceError) {
            _categories.value = pair.first.toList()
        }
    }

    fun addCategory(name: String) {
        val pair = dataSource.addCategory(name)
        if(pair.second == NullDataSourceError) {
            val list = _categories.value.toMutableList()
            list.add(pair.first)
            _categories.value = list
        }
    }
}