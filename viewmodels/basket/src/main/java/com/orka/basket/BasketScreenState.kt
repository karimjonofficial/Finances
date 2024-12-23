package com.orka.basket

sealed class BasketScreenState {

    data object Initial : BasketScreenState()
    data object Selling : BasketScreenState()

    sealed class Ready(open val basket: Basket) : BasketScreenState() {
        data class Regular(override val basket: Basket) : Ready(basket)
        data class Edit(override val basket: Basket) : Ready(basket)
    }
}