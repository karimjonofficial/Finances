package com.orka.basket.fixtures

import com.orka.basket.Basket
import com.orka.basket.BasketItem
import com.orka.core.BasketDataSource
import com.orka.products.Product

internal class BasketDataSourceImpl : BasketDataSource {

    private var empty = false

    override fun add(item: BasketItem) {

    }

    override fun remove(productId: Int) {

    }

    override fun decrease(productId: Int, amount: Int) {

    }

    override fun increase(productId: Int, amount: Int) {

    }

    override fun clear() {
        empty = !empty
    }

    override fun get(): Basket {

        return if(empty) Basket() else Basket(
            items = listOf(
                BasketItem(
                    product = Product(1, "Product 1", 100000.0, "", 1),
                    amount = 1000
                ),
                BasketItem(
                    product = Product(2, "Product 1", 100000.0, "", 1),
                    amount = 1000
                ),
                BasketItem(
                    product = Product(3, "Product 1", 100000.0, "", 1),
                    amount = 1000
                ),
                BasketItem(
                    product = Product(1, "Product 1", 100000.0, "", 1),
                    amount = 1000
                ),
                BasketItem(
                    product = Product(2, "Product 1", 100000.0, "", 1),
                    amount = 1000
                ),
                BasketItem(
                    product = Product(3, "Product 1", 100000.0, "", 1),
                    amount = 1000
                ),
                BasketItem(
                    product = Product(1, "Product 1", 100000.0, "", 1),
                    amount = 1000
                ),
                BasketItem(
                    product = Product(2, "Product 1", 100000.0, "", 1),
                    amount = 1000
                ),
                BasketItem(
                    product = Product(3, "Product 1", 100000.0, "", 1),
                    amount = 1000
                )
            ),
            price = 10000.0,
            comment = "Something in here"
        )
    }

    override fun comment(comment: String) {

    }

    override fun setPrice(price: Double) {

    }

}
