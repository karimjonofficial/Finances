package com.orka.finances.features.home.state

import com.orka.finances.features.home.data.models.CategoryModel

data class HomeScreenUiState(
    val categories: List<CategoryModel> = emptyList()
)