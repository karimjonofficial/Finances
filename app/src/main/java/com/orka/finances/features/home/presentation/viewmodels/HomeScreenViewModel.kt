package com.orka.finances.features.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.models.Category
import com.orka.finances.lib.errors.data.sources.NullDataSourceError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeScreenViewModel(private val dataSource: CategoriesDataSource) : ViewModel() {
    private val _categories: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    init {
        val pair = dataSource.getCategories()

        if(pair.second == NullDataSourceError) {
            _categories.value = pair.first
        } else {
            _categories.value = emptyList()
        }
    }

    fun addCategory(name: String, iconName: String = "", description: String = "") {
        val pair = dataSource.addCategory(name, iconName, description)

        if(pair.second == NullDataSourceError) {
            pair.first?.let {
                val list = _categories.value.toMutableList()
                list.add(it)
                _categories.value = list
            }
        }
    }
}