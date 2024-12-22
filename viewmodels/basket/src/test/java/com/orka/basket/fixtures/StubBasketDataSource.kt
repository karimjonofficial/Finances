package com.orka.basket.fixtures

import com.orka.basket.Basket
import com.orka.basket.BasketItem
import com.orka.core.BasketDataSource
import com.orka.products.Product

class StubBasketDataSource : BasketDataSource {
    override fun add(item: BasketItem) {}

    override fun remove(productId: Int) {}

    override fun decrease(productId: Int, amount: Int) {}

    override fun increase(productId: Int, amount: Int) {}

    override fun clear() {}

    override fun get(): Basket {
        return Basket(items = listOf(BasketItem(Product(1, "", 1.0, "", 1), 1)))
    }

    override fun comment(comment: String) {}
    override fun setPrice(price: Double) {
        TODO("Not yet implemented")
    }

}
