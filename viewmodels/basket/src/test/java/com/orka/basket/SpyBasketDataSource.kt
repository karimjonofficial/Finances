package com.orka.basket

import com.orka.core.BasketDataSource

class SpyBasketDataSource : BasketDataSource {

    val basket = Basket(comment = "Something")
    var getCalled = false
    var clearCalled = false

    override fun add(item: BasketItem) {
        TODO("Not yet implemented")
    }

    override fun remove(productId: Int) {
        TODO("Not yet implemented")
    }

    override fun decrease(productId: Int, amount: Int) {
        TODO("Not yet implemented")
    }

    override fun increase(productId: Int, amount: Int) {
        TODO("Not yet implemented")
    }

    override fun clear() {
        clearCalled = true
    }

    override fun get(): Basket {
        getCalled = true
        return basket
    }

    override fun comment(comment: String) {
        TODO("Not yet implemented")
    }

}
