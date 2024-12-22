package com.orka.basket

import com.orka.core.BaseViewModelWithInvoke
import com.orka.core.BasketDataSource
import com.orka.core.HttpService

class BasketScreenViewModel(
    basket: Basket,
    httpService: HttpService,
    private val basketDataSource: BasketDataSource
) : BaseViewModelWithInvoke<Basket>(basket, httpService) {

    fun clear() {
        basketDataSource.clear()
        setState(basketDataSource.get())
    }
}