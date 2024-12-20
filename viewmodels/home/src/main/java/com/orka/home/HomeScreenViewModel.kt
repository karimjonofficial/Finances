package com.orka.home

import com.orka.categories.Category
import com.orka.core.BaseViewModelWithFetch
import com.orka.core.CategoriesDataSource
import com.orka.core.HttpService

class HomeScreenViewModel(
    private val dataSource: CategoriesDataSource,
    httpService: HttpService,
    private val navigate: (Int) -> Unit,
) : BaseViewModelWithFetch<List<Category>>(emptyList(), httpService) {

    override fun fetch() {
        invoke { setState(dataSource.get() ?: emptyList()) }
    }

    fun select(category: Category) {
        navigate(category.id)
    }

    fun addCategory(name: String, description: String) {
        if (name.isNotBlank()) {
            invoke { if (dataSource.add(name, description) != null) fetch() }
        }
    }
}