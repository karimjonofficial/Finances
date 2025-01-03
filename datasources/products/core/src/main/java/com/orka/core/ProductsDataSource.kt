package com.orka.core

import com.orka.products.Product

interface ProductsDataSource {
    suspend fun getAll(): List<Product>?
    suspend fun getAll(categoryId: Int): List<Product>?
    suspend fun get(id: Int): Product
    suspend fun add(model: AddProductModel): Product?
    suspend fun update(model: UpdateProductModel): Product?
}