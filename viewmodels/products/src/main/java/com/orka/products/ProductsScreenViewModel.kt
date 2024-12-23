package com.orka.products

import com.orka.core.BaseViewModelWithFetch
import com.orka.core.HttpService
import com.orka.core.ProductsDataSource
import com.orka.log.Log

class ProductsScreenViewModel(
    private val categoryId: Int,
    private val dataSource: ProductsDataSource,
    httpService: HttpService
) : BaseViewModelWithFetch<List<Product>>(emptyList(), httpService) {

    override fun fetch() {
        invoke(
            request = { setState(dataSource.get(categoryId) ?: emptyList()) },
            onException = { Log("ProductsScreenViewModel.Http", it.message ?: "Unknown exception") }
        )
    }

    fun add(name: String, price: Double, description: String) {
        if (name.isNotBlank() && price > 0) {
            invoke(
                request = { dataSource.add(name, price, description, categoryId)?.let { fetch() } },
                onException = {
                    Log(
                        "ProductsScreenViewModel.Http",
                        it.message ?: "Unknown exception"
                    )
                }
            )
        }
    }
}

