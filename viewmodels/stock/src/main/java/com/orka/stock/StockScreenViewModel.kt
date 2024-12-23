package com.orka.stock

import com.orka.basket.BasketItem
import com.orka.core.BaseViewModelWithFetch
import com.orka.core.BasketDataSource
import com.orka.core.HttpService
import com.orka.core.ProductsDataSource
import com.orka.core.ReceiveDataSource
import com.orka.core.StockDataSource
import com.orka.core.models.PostReceiveRequestModel
import com.orka.core.models.PostReceiveRequestModelItem
import com.orka.log.Log
import com.orka.products.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class StockScreenViewModel(
    private val categoryId: Int,
    httpService: HttpService,
    private val stockDataSource: StockDataSource,
    private val receiveDataSource: ReceiveDataSource,
    private val productsDataSource: ProductsDataSource,
    private val basketDataSource: BasketDataSource,
    private val navigate: (Int) -> Unit
) : BaseViewModelWithFetch<List<StockItem>>(emptyList(), httpService) {

    private val _dialogState = MutableStateFlow<List<Product>>(emptyList())
    val dialogState = _dialogState.asStateFlow()

    override fun fetch() = invoke {
        setState(stockDataSource.get(categoryId) ?: emptyList())
        fetchProducts()
    }

    private fun fetchProducts() = invoke {
        _dialogState.value = productsDataSource.get(categoryId) ?: emptyList()
    }

    fun receive(productId: Int, amount: Int, price: Double, comment: String) {
        if (amount > 0 && price > 0.0) {
            invoke(
                request = {
                    receiveDataSource.add(
                        PostReceiveRequestModel(
                            items = listOf(PostReceiveRequestModelItem(productId, amount)),
                            price = price.toString(),
                            comment = comment
                        )
                    )
                },
                onException = { Log("StockScreenViewModel.Http", it.message ?: "No message") }
            )
        }
    }

    fun addToBasket(product: Product) {
        Log("BasketDataSource.${basketDataSource.hashCode()}", "Add")
        basketDataSource.add(BasketItem(product, 1))
    }

    fun select(item: StockItem) {
        navigate(item.id)
    }
}