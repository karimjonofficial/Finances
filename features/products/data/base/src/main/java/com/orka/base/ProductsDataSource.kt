package com.orka.base

interface ProductsDataSource {
    suspend fun get(): List<Product>?
    suspend fun add(name: String, price: Double, imgSrc: String): Product?
}