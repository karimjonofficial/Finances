package com.orka.products

sealed class ProductsScreenState {
    data object Initial : ProductsScreenState()
    data object Offline : ProductsScreenState()
    data object Initializing : ProductsScreenState()
    data object Empty : ProductsScreenState()
    data class Initialized(val productsMap: Map<Char, List<Product>>) : ProductsScreenState()
}