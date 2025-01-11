package com.orka.warehouse

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.orka.products.Product
import com.orka.stock.StockItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import com.orka.res.Strings
import com.orka.warehouse.WarehouseScreenViewModel as Fsm

sealed interface ProductsContentStates {
    fun addProduct(name: String, price: Double, comment: String, fsm: Fsm) {
        fsm.setProductsState(
            Processing(
                messageRes = Strings.processing_the_request,
                process = {
                    fsm.viewModelScope.launch(Dispatchers.IO) {
                        val result = fsm.addProduct(name, price, comment)
                        if(result != null) {
                            fsm.fetchProducts()
                        } else {
                            fsm.fetchProducts()
                        }
                    }
                }
            )
        )
    }
    fun refresh(fsm: Fsm) {
        fsm.viewModelScope.launch(Dispatchers.IO) {
            fsm.fetchProducts()
        }
    }

    data object Initial : ProductsContentStates {
        fun initialize(fsm: Fsm) {
            fsm.setProductsState(Processing(
                messageRes = Strings.initializing,
                process = { fsm.fetchProducts() }
            ))
        }
    }

    data class Processing(
        @StringRes val messageRes: Int,
        private val process: suspend () -> Unit
    ) : ProductsContentStates {
        fun process(fsm: Fsm) {
            fsm.viewModelScope.launch(Dispatchers.Default) {
                process()
            }
        }
    }

    data object Empty : ProductsContentStates {
        override fun refresh(fsm: Fsm) {

            fsm.setProductsState(Processing(
                messageRes = Strings.initializing,
                process = { fsm.fetchProducts() }
            ))
        }
    }

    data class Success(
        val productsMap: Map<Char, List<Product>>,
        val products: List<Product>
    ) : ProductsContentStates {

        override fun refresh(fsm: Fsm) {
            fsm.viewModelScope.launch(Dispatchers.Main) {
                fsm.viewModelScope.async(Dispatchers.IO) { fsm.getProducts() }.await()?.let {
                    if (it.isNotEmpty()) {
                        val sorted = it.sortedBy { product -> product.name.lowercase() }
                        fsm.setProductsState(Success(sorted.mapProducts(), sorted))
                    }
                }
            }
        }
        fun selectProduct(product: Product, fsm: Fsm) {
            fsm.selectProduct(product)
        }
    }

    data class Failure(
        val message: String
    ) : ProductsContentStates {
        fun retry(fsm: Fsm) {
            fsm.setProductsState(Processing(
                messageRes = Strings.initializing,
                process = { fsm.fetchProducts() }
            ))
        }
    }
}

sealed interface StockContentStates {

    fun receive(productId: Int, amount: Int, price: Double, comment: String, fsm: Fsm) {
        fsm.setStockState(
            state = Processing(
                message = "Processing",
                process = {
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
    fun refresh(fsm: Fsm) {
        fsm.viewModelScope.launch(Dispatchers.IO) {
            fsm.fetchStock()
        }
    }

    data object Initial : StockContentStates {
        fun initialize(fsm: Fsm) {
            fsm.setStockState(Processing(
                message = "Initializing",
                process = { fsm.fetchStock() }
            ))
        }
    }

    data class Processing(
        val message: String,
        private val process: suspend () -> Unit
    ) : StockContentStates {
        fun process(fsm: Fsm) {
            fsm.viewModelScope.launch(Dispatchers.Default) {
                process()
            }
        }
    }

    data object Empty : StockContentStates {
        override fun refresh(fsm: Fsm) {

            fsm.setStockState(Processing(
                message = "Initializing",
                process = { fsm.fetchStock() }
            ))
        }
    }

    data class Success(val stockItemsMap: Map<Char, List<StockItem>>) : StockContentStates {
        override fun refresh(fsm: Fsm) {
            fsm.viewModelScope.launch(Dispatchers.Main) {
                fsm.viewModelScope.async(Dispatchers.IO) { fsm.getStockItems() }.await()?.let {
                    if (it.isNotEmpty()) {
                        fsm.setStockState(Success(it.mapStockItems()))
                    }
                }
            }
        }
        fun addToBasket(stockItem: StockItem, fsm: Fsm) {
            fsm.addToBasket(stockItem)
        }
    }

    data class Failure(val message: String) : StockContentStates {
        fun retry(fsm: Fsm) {
            fsm.setStockState(Processing(
                message = "Initializing",
                process = { fsm.fetchStock() }
            ))
        }
    }
}

private suspend fun Fsm.fetchStock() {
    try {
        val r = viewModelScope.async(Dispatchers.IO) { getStockItems() }.await()
        if (r?.isNotEmpty() == true) setStockState(StockContentStates.Success(r.mapStockItems()))
        else setStockState(StockContentStates.Empty)
    } catch (e: Exception) {
        setStockState(StockContentStates.Failure(e.message ?: "No message provided"))
    }
}
private suspend fun Fsm.fetchProducts() {
    try {
        val r = viewModelScope.async(Dispatchers.IO) { getProducts() }.await()
        if (r?.isNotEmpty() == true) {
            val sorted = r.sortedBy { it.name.lowercase() }
            setProductsState(ProductsContentStates.Success(sorted.mapProducts(), sorted))
        } else setProductsState(ProductsContentStates.Empty)
    } catch (e: Exception) {
        setProductsState(ProductsContentStates.Failure(e.message ?: "No message provided"))
    }
}
private fun List<Product>.mapProducts(): Map<Char, List<Product>> {
    return this.groupBy { it.name[0].uppercaseChar() }
}
private fun List<StockItem>.mapStockItems(): Map<Char, List<StockItem>> {
    return this.sortedBy { it.product.name.lowercase() }
        .groupBy { it.product.name[0].uppercaseChar() }
}