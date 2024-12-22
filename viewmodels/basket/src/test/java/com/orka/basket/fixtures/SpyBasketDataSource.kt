package com.orka.basket.fixtures

import com.orka.basket.Basket
import com.orka.basket.BasketItem
import com.orka.core.BasketDataSource

class SpyBasketDataSource : BasketDataSource {

    val basket = Basket(comment = "Something")

    var getCalled = false
    var clearCalled = false
    var increaseCalled = false
    var decreaseCalled = false
    var removeCalled = false
    var commentCalled = false

    var increaseAmount = 0
    var decreaseAmount = 0

    override fun add(item: BasketItem) {}

    override fun remove(productId: Int) {
        removeCalled = true
    }

    override fun decrease(productId: Int, amount: Int) {
        decreaseCalled = true
        decreaseAmount = amount
    }

    override fun increase(productId: Int, amount: Int) {
        increaseCalled = true
        increaseAmount = amount
    }

    override fun clear() {
        clearCalled = true
    }

    override fun get(): Basket {
        getCalled = true
        return basket
    }

    override fun comment(comment: String) {
        commentCalled = true
    }

    override fun setPrice(price: Double) {
        TODO("Not yet implemented")
    }
}
