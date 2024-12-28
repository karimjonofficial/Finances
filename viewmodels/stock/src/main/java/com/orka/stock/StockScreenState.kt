package com.orka.stock

import com.orka.products.Product

sealed class StockScreenState {
    data object Initial : StockScreenState()
    data object Offline : StockScreenState()
    data object Initializing : StockScreenState()
    data object Empty : StockScreenState()
    data class Initialized(val stockItemsMap: Map<Char, List<StockItem>>) : StockScreenState()
}

sealed class DialogState {
    data object Initial : DialogState()
    data object Offline : DialogState()
    data object Initializing : DialogState()
    data object Empty : DialogState()
    data class Initialized(val products: List<Product>) : DialogState()
}