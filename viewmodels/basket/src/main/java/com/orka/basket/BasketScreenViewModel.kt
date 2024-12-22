package com.orka.basket

import com.orka.core.BaseViewModelWithFetch
import com.orka.core.BasketDataSource
import com.orka.core.HttpService

class BasketScreenViewModel(
    httpService: HttpService,
    private val basketDataSource: BasketDataSource,
) : BaseViewModelWithFetch<BasketScreenState>(
    BasketScreenState.Regular(Basket()), httpService
) {

    init {
        fetch()
    }

    override fun fetch() {

        val basket = basketDataSource.get()

        setState(
            if (uiState.value is BasketScreenState.Regular)
                BasketScreenState.Regular(basket)
            else BasketScreenState.Edit(basket)
        )
    }

    fun clear() {
        basketDataSource.clear()
        setState(BasketScreenState.Regular(basketDataSource.get()))
    }

    fun increase(productId: Int) {
        basketDataSource.increase(productId, 1)
        setState(BasketScreenState.Regular(basketDataSource.get()))
    }

    fun decrease(productId: Int) {
        basketDataSource.decrease(productId, 1)
        setState(BasketScreenState.Regular(basketDataSource.get()))
    }

    fun remove(productId: Int) {
        basketDataSource.remove(productId)
        setState(BasketScreenState.Regular(basketDataSource.get()))
    }

    fun setPrice(price: Double) {
        basketDataSource.setPrice(price)
        setState(BasketScreenState.Regular(basketDataSource.get()))
    }

    fun edit() {
        setState(BasketScreenState.Edit(uiState.value.basket))
    }
}