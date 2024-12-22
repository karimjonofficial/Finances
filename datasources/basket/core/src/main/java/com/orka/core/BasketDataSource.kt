package com.orka.core

import com.orka.basket.Basket
import com.orka.basket.BasketItem

interface BasketDataSource {
    fun add(item: BasketItem)
    fun remove(productId: Int)
    fun decrease(productId: Int, amount: Int)
    fun increase(productId: Int, amount: Int)
    fun clear()
    fun get(): Basket
    fun comment(comment: String)
    fun setPrice(price: Double)
}