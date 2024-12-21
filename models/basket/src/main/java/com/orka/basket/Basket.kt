package com.orka.basket

data class Basket(
    val items: List<BasketItem> = emptyList(),
    val price: Double = 0.0,
    val comment: String = ""
)