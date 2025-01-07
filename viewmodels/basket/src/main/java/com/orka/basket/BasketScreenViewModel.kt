package com.orka.basket

import com.orka.core.BasketDataSource
import com.orka.core.HttpService
import com.orka.core.SaleDataSource
import com.orka.core.SingleStateViewModel
import com.orka.core.models.PostSaleRequestModel
import com.orka.core.models.PostSaleRequestModelItem
import com.orka.log.Log

class BasketScreenViewModel(
    httpService: HttpService,
    private val basketDataSource: BasketDataSource,
    private val saleDataSource: SaleDataSource
) : SingleStateViewModel<BasketScreenState>(
    httpService = httpService,
    BasketScreenState.Initial
) {

    init { fetch() }

    fun fetch() {
        uiState.value.let {
            if (it !is BasketScreenState.InProcess) {
                if (it is BasketScreenState.WithBasket) {
                    setState(
                        if (it is BasketScreenState.WithBasket.Edit)
                            BasketScreenState.WithBasket.Edit(basketDataSource.get())
                        else BasketScreenState.WithBasket.Regular(basketDataSource.get())
                    )
                } else if (it is BasketScreenState.Initial) {
                    setState(BasketScreenState.WithBasket.Regular(basketDataSource.get()))
                }
            }
        }
    }

    fun clear() {
        basketDataSource.clear()
        setState(BasketScreenState.Initial)
        fetch()
    }

    fun increase(productId: Int) {
        if (uiState.value is BasketScreenState.WithBasket) {
            basketDataSource.increase(productId, 1)
            setState(BasketScreenState.WithBasket.Regular(basketDataSource.get()))
        }
    }

    fun decrease(productId: Int) {
        if (uiState.value is BasketScreenState.WithBasket) {
            basketDataSource.decrease(productId, 1)
            setState(BasketScreenState.WithBasket.Regular(basketDataSource.get()))
        }
    }

    fun remove(productId: Int) {

        if (uiState.value is BasketScreenState.WithBasket) {
            basketDataSource.remove(productId)
            setState(BasketScreenState.WithBasket.Regular(basketDataSource.get()))
        }
    }

    fun setPrice(price: Double) {

        if (uiState.value is BasketScreenState.WithBasket) {
            basketDataSource.setPrice(price)
            setState(BasketScreenState.WithBasket.Regular(basketDataSource.get()))
        }
    }

    fun edit() {
        uiState.value.let {
            if (it is BasketScreenState.WithBasket)
                setState(BasketScreenState.WithBasket.Edit(it.basket))
        }
    }

    fun stopEditing() {
        if (uiState.value is BasketScreenState.WithBasket) {
            setState(BasketScreenState.WithBasket.Regular(basketDataSource.get()))
        }
    }

    fun sell() {
        val state = uiState.value

        state.let {
            if (it is BasketScreenState.WithBasket && it.basket.correct()) {
                setState(BasketScreenState.InProcess)
                request(
                    request = {
                        sell(basketDataSource.get())
                        clear()
                    },
                    onException = { exception ->
                        Log("BasketScreenViewModel.Http", exception.message ?: "No message")
                    }
                )
            }
        }
    }

    private fun Basket.correct() = this.items.isNotEmpty() && this.price > 0.0 && this.price.toInt() < Int.MAX_VALUE

    private suspend fun sell(basket: Basket) {
        saleDataSource.add(
            body = PostSaleRequestModel(
                items = basket.items.map {
                    PostSaleRequestModelItem(
                        amount = it.amount,
                        productId = it.product.id
                    )
                },
                price = basket.price.toString(),
                comment = "No comment"
            )
        )
    }
}