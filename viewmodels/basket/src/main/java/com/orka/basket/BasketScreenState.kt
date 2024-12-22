package com.orka.basket

sealed class BasketScreenState(open val basket: Basket) {
    data class Regular(override val basket: Basket) : BasketScreenState(basket)
    data class Edit(override val basket: Basket) : BasketScreenState(basket)
}