package com.orka.warehouse

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.orka.products.Product
import com.orka.res.Strings
import com.orka.stock.StockItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import com.orka.warehouse.WarehouseScreenViewModel as Fsm

sealed interface ProductsContentStates {
    val fsm: Fsm

    fun addProductAndReceive(name: String, price: Double, comment: String, amount: Int) {
        fsm.setProductsState(
            Processing(
                fsm = fsm,
                messageRes = Strings.processing_the_request,
                callback = {
                    fsm.viewModelScope.launch(Dispatchers.IO) {
                        val result = fsm.addProductAndReceive(name, price, comment, amount)
                        if(result.first != null) fsm.fetchProducts()
                        if(result.second != null) fsm.fetchStock()
                    }
                }
            )
        )
    }
    fun refresh() {
        fsm.viewModelScope.launch(Dispatchers.IO) {
            fsm.fetchProducts()
        }
    }

    data class Initial(override val fsm: Fsm) : ProductsContentStates {
        fun initialize() {
            fsm.setProductsState(Processing(
                fsm = fsm,
                messageRes = Strings.initializing,
                callback = { fsm.fetchProducts() }
            ))
        }
    }

    data class Processing(
        override val fsm: Fsm,
        @StringRes val messageRes: Int,
        private val callback: suspend () -> Unit
    ) : ProductsContentStates {
        fun process() {
            fsm.viewModelScope.launch(Dispatchers.Default) {
                callback()
            }
        }
    }

    data class Empty(override val fsm: Fsm) : ProductsContentStates {
        override fun refresh() {
            fsm.setProductsState(Processing(
                fsm = fsm,
                messageRes = Strings.initializing,
                callback = { fsm.fetchProducts() }
            ))
        }
    }

    data class Success(
        override val fsm: Fsm,
        val productsMap: Map<Char, List<Product>>,
        val products: List<Product>
    ) : ProductsContentStates {

        override fun refresh() {
            fsm.viewModelScope.launch(Dispatchers.Main) {
                fsm.viewModelScope.async(Dispatchers.IO) { fsm.getProducts() }.await()?.let {
                    if (it.isNotEmpty()) {
                        val sorted = it.sortedBy { product -> product.name.lowercase() }
                        fsm.setProductsState(Success(fsm, sorted.mapProducts(), sorted))
                    }
                }
            }
        }
        fun selectProduct(product: Product) {
            fsm.selectProduct(product)
        }
    }

    data class Failure(
        override val fsm: Fsm,
        val message: String
    ) : ProductsContentStates {
        fun retry() {
            fsm.setProductsState(Processing(
                fsm = fsm,
                messageRes = Strings.initializing,
                callback = { fsm.fetchProducts() }
            ))
        }
    }
}

sealed interface StockContentStates {
    val fsm: Fsm

    fun receive(productId: Int, amount: Int, price: Double, comment: String) {
        fsm.setStockState(
            state = Processing(
                fsm = fsm,
                messageRes = Strings.processing_the_request,
                callback = {
                    val result = fsm.receive(productId, amount, price, comment)
                    if (result != null) {
                        fsm.fetchStock()
                    } else {
                        fsm.fetchStock()
                    }
                }
            )
        )
    }
    fun refresh() {
        fsm.viewModelScope.launch(Dispatchers.IO) {
            fsm.fetchStock()
        }
    }

    data class Initial(override val fsm: Fsm) : StockContentStates {
        fun initialize() {
            fsm.setStockState(Processing(
                fsm = fsm,
                messageRes = Strings.initializing,
                callback = { fsm.fetchStock() }
            ))
        }
    }

    data class Processing(
        override val fsm: Fsm,
        @StringRes val messageRes: Int,
        private val callback: suspend () -> Unit
    ) : StockContentStates {
        fun process() {
            fsm.viewModelScope.launch(Dispatchers.Default) {
                callback()
            }
        }
    }

    data class Empty(override val fsm: Fsm) : StockContentStates {
        override fun refresh() {

            fsm.setStockState(Processing(
                fsm = fsm,
                messageRes = Strings.initializing,
                callback = { fsm.fetchStock() }
            ))
        }
    }

    data class Success(
        override val fsm: Fsm,
        val stockItemsMap: Map<Char, List<StockItem>>
    ) : StockContentStates {
        override fun refresh() {
            fsm.viewModelScope.launch(Dispatchers.Main) {
                fsm.viewModelScope.async(Dispatchers.IO) { fsm.getStockItems() }.await()?.let {
                    if (it.isNotEmpty()) {
                        fsm.setStockState(Success(fsm, it.mapStockItems()))
                    }
                }
            }
        }
        fun addToBasket(stockItem: StockItem) {
            fsm.addToBasket(stockItem)
        }
    }

    data class Failure(override val fsm: Fsm, val message: String) : StockContentStates {
        fun retry() {
            fsm.setStockState(Processing(
                fsm = fsm,
                messageRes = Strings.initializing,
                callback = { fsm.fetchStock() }
            ))
        }
    }
}

private suspend fun Fsm.fetchStock() {
    try {
        val r = viewModelScope.async(Dispatchers.IO) { getStockItems() }.await()
        if (r?.isNotEmpty() == true) setStockState(StockContentStates.Success(this, r.mapStockItems()))
        else setStockState(StockContentStates.Empty(this))
    } catch (e: Exception) {
        setStockState(StockContentStates.Failure(this, e.message ?: "No message provided"))
    }
}
private suspend fun Fsm.fetchProducts() {
    try {
        val r = viewModelScope.async(Dispatchers.IO) { getProducts() }.await()
        if (r?.isNotEmpty() == true) {
            val sorted = r.sortedBy { it.name.lowercase() }
            setProductsState(ProductsContentStates.Success(this, sorted.mapProducts(), sorted))
        } else setProductsState(ProductsContentStates.Empty(this))
    } catch (e: Exception) {
        setProductsState(ProductsContentStates.Failure(this, e.message ?: "No message provided"))
    }
}
private fun List<Product>.mapProducts(): Map<Char, List<Product>> {
    return this.groupBy { it.name[0].uppercaseChar() }
}
private fun List<StockItem>.mapStockItems(): Map<Char, List<StockItem>> {
    return this.sortedBy { it.product.name.lowercase() }
        .groupBy { it.product.name[0].uppercaseChar() }
}