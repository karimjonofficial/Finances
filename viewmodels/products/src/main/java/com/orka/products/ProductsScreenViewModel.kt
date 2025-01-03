package com.orka.products

import com.orka.core.AddProductModel
import com.orka.core.HttpService
import com.orka.core.ProductsDataSource
import com.orka.core.SingleStateViewModel
import com.orka.log.Log

class ProductsScreenViewModel(
    private val categoryId: Int,
    private val dataSource: ProductsDataSource,
    httpService: HttpService,
    private val navigate: (Int) -> Unit
) : SingleStateViewModel<ProductsScreenState>(httpService, ProductsScreenState.Initial) {

    fun fetch() {
        launch {
            if(uiState.value == ProductsScreenState.Initial)
                setState(ProductsScreenState.Initializing)

            request(
                request = {
                    val result = dataSource.getAll(categoryId)?.sortedBy { it.name }?.groupBy { it.name[0] }
                    if(result?.isNotEmpty() == true) {
                        launch { setState(ProductsScreenState.Initialized(result)) }
                    } else {
                        launch { setState(ProductsScreenState.Empty) }
                    }
                },
                onException = {
                    setState(ProductsScreenState.Offline)
                    Log("ProductsScreenViewModel.Http", it.message ?: "Unknown exception")
                }
            )
        }
    }

    fun add(name: String, price: Double, description: String) {
        if (name.isNotBlank() && price > 0) {
            request(
                request = { dataSource.add(AddProductModel(name, price, description, categoryId))?.let { fetch() } },
                onException = {
                    Log(
                        "ProductsScreenViewModel.Http",
                        it.message ?: "Unknown exception"
                    )
                }
            )
        }
    }

    fun select(product: Product) {
        navigate(product.id)
    }
}

