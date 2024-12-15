package com.orka.base

interface ProductsDataSource {
    suspend fun get(categoryId: Int): List<Product>?
    suspend fun add(name: String, price: Double, description: String, imgSrc: String): Product?
}