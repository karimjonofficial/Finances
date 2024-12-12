package com.orka.base

interface CategoriesDataSource {
    suspend fun get(): List<Category>?
    suspend fun add(name: String, description: String): Category?
}