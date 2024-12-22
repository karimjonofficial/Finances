package com.orka.basket

import com.orka.core.BaseViewModelWithFetch
import com.orka.core.BasketDataSource
import com.orka.core.HttpService
import com.orka.log.Log

class BasketScreenViewModel(
    httpService: HttpService,
    private val basketDataSource: BasketDataSource,
) : BaseViewModelWithFetch<BasketScreenState>(BasketScreenState.Regular(Basket()), httpService) {

    init {
        Log("BasketDataSource.${basketDataSource.hashCode()}", "Get")
        fetch()
    }

    override fun fetch() {
        setState(BasketScreenState.Regular(basketDataSource.get()))
    }

    fun clear() {
        Log("BasketDataSource.${basketDataSource.hashCode()}", "Clear")
        basketDataSource.clear()
        Log("BasketDataSource.${basketDataSource.hashCode()}", "Get")
        setState(BasketScreenState.Regular(basketDataSource.get()))
    }

    fun increase(productId: Int) {
        Log("BasketDataSource.${basketDataSource.hashCode()}", "Decrease")
        basketDataSource.increase(productId, 1)
        Log("BasketDataSource.${basketDataSource.hashCode()}", "Get")
        setState(BasketScreenState.Regular(basketDataSource.get()))
    }

    fun decrease(productId: Int) {
        Log("BasketDataSource.${basketDataSource.hashCode()}", "Decrease")
        basketDataSource.decrease(productId, 1)
        Log("BasketDataSource.${basketDataSource.hashCode()}", "Get")
        setState(BasketScreenState.Regular(basketDataSource.get()))
    }

    fun remove(productId: Int) {
        Log("BasketDataSource.${basketDataSource.hashCode()}", "Remove")
        basketDataSource.remove(productId)
        Log("BasketDataSource.${basketDataSource.hashCode()}", "Get")
        setState(BasketScreenState.Regular(basketDataSource.get()))
    }

    fun setPrice(price: Double) {
        Log("BasketDataSource.${basketDataSource.hashCode()}", "SetPrice")
        basketDataSource.setPrice(price)
        Log("BasketDataSource.${basketDataSource.hashCode()}", "Get")
        setState(BasketScreenState.Regular(basketDataSource.get()))
    }

    fun edit() {
        setState(BasketScreenState.Edit(uiState.value.basket))
    }
}