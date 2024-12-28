package com.orka.home

import com.orka.categories.Category
import com.orka.core.CategoriesDataSource
import com.orka.core.HttpService
import com.orka.core.SingleStateViewModel

class HomeScreenViewModel(
    private val dataSource: CategoriesDataSource,
    httpService: HttpService,
    private val navigate: (Int) -> Unit,
) : SingleStateViewModel<HomeScreenState>(httpService, HomeScreenState.Initial) {

    fun fetch() {
        launch {
            if(uiState.value == HomeScreenState.Initial)
                setState(HomeScreenState.Initializing)
        }
        request {
            val result = dataSource.get()
            if(result != null) {
                if(result.isNotEmpty()) {
                    launch {
                        setState(HomeScreenState.Initialized(result.sortedBy { it.name }))
                    }
                } else {
                    launch { setState(HomeScreenState.Empty) }
                }
            } else {
                launch { setState(HomeScreenState.Offline) }
            }
        }
    }

    fun select(category: Category) {
        navigate(category.id)
    }

    fun addCategory(name: String, description: String) {
        if (name.isNotBlank()) {
            request { if (dataSource.add(name, description) != null) fetch() }
        }
    }

    fun reset() {
        launch { setState(HomeScreenState.Initial) }
    }
}