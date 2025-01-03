package com.orka.product

import com.orka.core.HttpService
import com.orka.core.ProductsDataSource
import com.orka.core.SingleStateFsm

class ProductScreenViewModel(
    internal val dataSource: ProductsDataSource,
    productId: Int,
    internal val httpService: HttpService
) : SingleStateFsm<ProductScreenStates, ProductScreenEvents, ProductScreenViewModel>(ProductScreenStates.Initial(productId))