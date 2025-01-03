package com.orka.stock

import com.orka.basket.BasketItem
import com.orka.core.BasketDataSource
import com.orka.core.DoubleStateViewModel
import com.orka.core.HttpService
import com.orka.core.ProductsDataSource
import com.orka.core.ReceiveDataSource
import com.orka.core.StockDataSource
import com.orka.core.models.PostReceiveRequestModel
import com.orka.core.models.PostReceiveRequestModelItem
import com.orka.log.Log

class StockScreenViewModel(
    private val categoryId: Int,
    httpService: HttpService,
    private val stockDataSource: StockDataSource,
    private val receiveDataSource: ReceiveDataSource,
    private val productsDataSource: ProductsDataSource,
    private val basketDataSource: BasketDataSource
) : DoubleStateViewModel<StockScreenState, DialogState>(
    httpService = httpService,
    initialPrimaryState = StockScreenState.Initial,
    initialSecondaryState = DialogState.Initial
) {
    val stockItemsState = primaryState
    val dialogState = secondaryState

    fun fetch() = request {
        fetchStockItems()
        fetchProducts()
    }

    private fun fetchProducts() {
        launch {
            if(secondaryState.value == DialogState.Initial)
                setSecondaryState(DialogState.Initializing)

            request(
                request = {
                    val result = productsDataSource.getAll(categoryId)
                    if (result?.isNotEmpty() == true) {
                        launch { setSecondaryState(DialogState.Initialized(result.sortedBy { it.name })) }
                    } else {
                        launch { setSecondaryState(DialogState.Empty) }
                    }
                },
                onException = {
                    setSecondaryState(DialogState.Offline)
                    Log("ProductsScreenViewModel.Http", it.message ?: "Unknown exception")
                }
            )
        }
    }
    private fun fetchStockItems() {
        launch {
            if(primaryState.value == StockScreenState.Initial)
                setPrimaryState(StockScreenState.Initializing)

            request(
                request = {
                    val result = stockDataSource.get(categoryId)?.sortedBy { it.product.name }
                        ?.groupBy { it.product.name[0] }
                    if (result?.isNotEmpty() == true) {
                        launch { setPrimaryState(StockScreenState.Initialized(result)) }
                    } else {
                        launch { setPrimaryState(StockScreenState.Empty) }
                    }
                },
                onException = {
                    setPrimaryState(StockScreenState.Offline)
                    Log("ProductsScreenViewModel.Http", it.message ?: "Unknown exception")
                }
            )
        }
    }

    fun receive(productId: Int, amount: Int, price: Double, comment: String) {
        if (amount > 0 && price > 0.0) {
            request(
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

    fun select(stockItem: StockItem) {
        launch {
            Log("BasketDataSource.${basketDataSource.hashCode()}", "Add")
            basketDataSource.add(BasketItem(stockItem.product, 1))
        }
    }
}