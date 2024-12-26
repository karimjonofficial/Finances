package com.orka.home

import com.orka.categories.Category
import com.orka.core.CategoriesDataSource
import com.orka.core.HttpService
import com.orka.core.ListStateViewModel

class HomeScreenViewModel(
    private val dataSource: CategoriesDataSource,
    httpService: HttpService,
    private val navigate: (Int) -> Unit,
) : ListStateViewModel<Category>(httpService) {

    fun fetch() {
        request { setState(dataSource.get() ?: emptyList()) }
    }

    fun select(category: Category) {
        navigate(category.id)
    }

    fun addCategory(name: String, description: String) {
        if (name.isNotBlank()) {
            request { if (dataSource.add(name, description) != null) fetch() }
        }
    }
}