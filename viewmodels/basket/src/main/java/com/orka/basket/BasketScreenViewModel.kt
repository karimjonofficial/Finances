package com.orka.basket

import com.orka.core.BaseViewModelWithFetch
import com.orka.core.BasketDataSource
import com.orka.core.HttpService
import com.orka.core.SaleDataSource
import com.orka.core.models.PostSaleRequestModel
import com.orka.core.models.PostSaleRequestModelItem
import com.orka.log.Log

class BasketScreenViewModel(
    httpService: HttpService,
    private val basketDataSource: BasketDataSource,
    private val saleDataSource: SaleDataSource
) : BaseViewModelWithFetch<BasketScreenState>(
    initialState = BasketScreenState.Initial,
    httpService = httpService
) {

    init {
        fetch()
    }

    override fun fetch() {
        uiState.value.let {
            if (it !is BasketScreenState.Selling) {
                if (it is BasketScreenState.Ready) {
                    setState(
                        if (it is BasketScreenState.Ready.Edit)
                            BasketScreenState.Ready.Edit(basketDataSource.get())
                        else BasketScreenState.Ready.Regular(basketDataSource.get())
                    )
                } else if (it is BasketScreenState.Initial) {
                    setState(BasketScreenState.Ready.Regular(basketDataSource.get()))
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
        if (uiState.value is BasketScreenState.Ready) {
            basketDataSource.increase(productId, 1)
            setState(BasketScreenState.Ready.Regular(basketDataSource.get()))
        }
    }

    fun decrease(productId: Int) {
        if (uiState.value is BasketScreenState.Ready) {
            basketDataSource.decrease(productId, 1)
            setState(BasketScreenState.Ready.Regular(basketDataSource.get()))
        }
    }

    fun remove(productId: Int) {

        if (uiState.value is BasketScreenState.Ready) {
            basketDataSource.remove(productId)
            setState(BasketScreenState.Ready.Regular(basketDataSource.get()))
        }
    }

    fun setPrice(price: Double) {

        if (uiState.value is BasketScreenState.Ready) {
            basketDataSource.setPrice(price)
            setState(BasketScreenState.Ready.Regular(basketDataSource.get()))
        }
    }

    fun edit() {
        uiState.value.let {
            if (it is BasketScreenState.Ready)
                setState(BasketScreenState.Ready.Edit(it.basket))
        }
    }

    fun stopEditing() {
        if (uiState.value is BasketScreenState.Ready) {
            setState(BasketScreenState.Ready.Regular(basketDataSource.get()))
        }
    }

    fun sale() {
        val state = uiState.value

        state.let {
            if (it is BasketScreenState.Ready && it.basket.items.isNotEmpty() && it.basket.price > 0.0) {
                setState(BasketScreenState.Selling)
                invoke(
                    request = {
                        sale(basketDataSource.get())
                        clear()
                    },
                    onException = { exception ->
                        Log("BasketScreenViewModel.Http", exception.message ?: "No message")
                    }
                )
            }
        }
    }

    private suspend fun sale(basket: Basket) {
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