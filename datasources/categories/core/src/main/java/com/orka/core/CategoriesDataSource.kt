package com.orka.core

import com.orka.categories.Category

interface CategoriesDataSource {
    suspend fun get(): List<Category>?
    suspend fun add(name: String, description: String): Category?
}