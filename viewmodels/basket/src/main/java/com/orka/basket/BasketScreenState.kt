package com.orka.basket

sealed class BasketScreenState {

    data object Initial : BasketScreenState()
    data object InProcess : BasketScreenState()

    sealed class WithBasket(open val basket: Basket) : BasketScreenState() {
        data class Regular(override val basket: Basket) : WithBasket(basket)
        data class Edit(override val basket: Basket) : WithBasket(basket)
    }
}