package com.orka.finances.features.products.data.sources

import com.orka.finances.features.products.models.Product

interface ProductsDataSource {
    fun get(categoryId: Int): List<Product>?
}