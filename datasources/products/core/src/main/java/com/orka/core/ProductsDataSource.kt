package com.orka.core

import com.orka.products.Product

interface ProductsDataSource {
    suspend fun get(): List<Product>?
    suspend fun get(categoryId: Int): List<Product>?
    suspend fun add(name: String, price: Double, description: String, categoryId: Int): Product?
}