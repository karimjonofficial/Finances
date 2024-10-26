package com.orka.finances.features.home.data.sources

import com.orka.finances.features.home.models.Category

interface CategoriesDataSource {
    suspend fun get(token: String): List<Category>?
}