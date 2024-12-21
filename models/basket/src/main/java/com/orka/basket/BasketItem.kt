package com.orka.basket

import com.orka.products.Product

data class BasketItem(
    val product: Product,
    val amount: Int
)
