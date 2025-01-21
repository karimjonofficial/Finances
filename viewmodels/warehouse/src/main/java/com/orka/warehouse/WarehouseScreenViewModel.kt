package com.orka.warehouse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orka.basket.BasketItem
import com.orka.core.AddProductModel
import com.orka.core.BasketDataSource
import com.orka.core.HttpService
import com.orka.core.ProductsDataSource
import com.orka.core.ReceiveDataSource
import com.orka.core.StockDataSource
import com.orka.core.models.PostReceiveRequestModel
import com.orka.core.models.PostReceiveRequestModelItem
import com.orka.log.Log
import com.orka.products.Product
import com.orka.receive.Receive
import com.orka.stock.StockItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WarehouseScreenViewModel(
    private val categoryId: Int,
    private val httpService: HttpService,
    private val productsDataSource: ProductsDataSource,
    private val stockDataSource: StockDataSource,
    private val receiveDataSource: ReceiveDataSource,
    private val basketDataSource: BasketDataSource,
    private val navigateToProductScreen: (Int) -> Unit
) : ViewModel() {

    private val _productsUiState =
        MutableStateFlow<ProductsContentStates>(ProductsContentStates.Initial(this))
    val productsUiState = _productsUiState.asStateFlow()

    private val _stockUiState = MutableStateFlow<StockContentStates>(StockContentStates.Initial(this))
    val stockUiState = _stockUiState.asStateFlow()

    fun refresh() {
        _stockUiState.value.refresh()
        _productsUiState.value.refresh()
    }

    fun selectProduct(product: Product) {
        navigateToProductScreen(product.id)
    }

    fun addToBasket(stockItem: StockItem) {
        basketDataSource.add(BasketItem(stockItem.product, 1))
    }

    internal fun setProductsState(state: ProductsContentStates) {
        viewModelScope.launch(Dispatchers.Default) {
            _productsUiState.value = state
        }
    }
    internal fun setStockState(state: StockContentStates) {
        viewModelScope.launch(Dispatchers.Default) {
            _stockUiState.value = state
        }
    }
    internal suspend fun receive(productId: Int, amount: Int, price: Double, comment: String): Receive? {
        return if (amount > 0 && price > 0.0) {
            request {
                receiveDataSource.add(
                    PostReceiveRequestModel(
                        items = listOf(PostReceiveRequestModelItem(productId, amount)),
                        price = price.toString(),
                        comment = comment
                    )
                )
            }
        } else null
    }
    internal suspend fun getProducts(): List<Product>? {
        return httpService.invoke {
            productsDataSource.getAll(categoryId)
        }
    }
    internal suspend fun getStockItems(): List<StockItem>? {
        return stockDataSource.get(categoryId)
    }
    private suspend fun addProduct(name: String, price: Double, comment: String): Product? {
        return request {
                productsDataSource.add(
                    AddProductModel(
                        name = name,
                        price = price,
                        description = comment,
                        categoryId = categoryId
                    )
                )
            }
    }
    private suspend fun <T> request(request: suspend () -> T?): T? {
        return httpService.invoke(
            request = request,
            onException = { e ->
                Log("WarehouseScreen.Http", "Exception: ${e.message}")
                null
            }
        )
    }

    internal suspend fun addProductAndReceive(
        name: String,
        price: Double,
        comment: String,
        amount: Int
    ): Pair<Product?, Receive?> {
        val product = addProduct(name, price, comment)

        if (product != null) {
            if (amount > 0) {
                val receive = receive(
                    product.id,
                    amount,
                    (product.price * amount),
                    "Initial amount"
                )

                if(receive != null) {
                    return Pair(product, receive)
                } else Pair(product, null)
            }
            return Pair(product, null)
        }
        return Pair(null, null)
    }
}