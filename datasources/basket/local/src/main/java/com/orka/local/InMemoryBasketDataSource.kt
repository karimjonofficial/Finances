package com.orka.local

import com.orka.basket.Basket
import com.orka.basket.BasketItem
import com.orka.core.BasketDataSource

class InMemoryBasketDataSource internal constructor(): BasketDataSource {

    private var profit = 0.0

    private val items: MutableList<BasketItem> = emptyList<BasketItem>().toMutableList()
    private var comment: String = ""

    override fun add(item: BasketItem) {
        if(item.amount > 0) {
            items.find { it.product.id == item.product.id }?.let { oldItem ->
                val new = oldItem.copy(amount = oldItem.amount + item.amount)
                items.remove(oldItem)
                items.add(new)
            } ?: items.add(item)
        } else {
            throw Exception()
        }
    }

    override fun remove(productId: Int) {
        if(items.isNotEmpty()) {
            items.find { it.product.id == productId }?.let {
                items.remove(it)
            } ?: throw Exception()
        } else {
            throw Exception()
        }
    }

    override fun decrease(productId: Int, amount: Int) {
        items.find { it.product.id == productId }?.let {
            if(amount > it.amount) {
                throw Exception()
            } else {
                if(amount < it.amount) {
                    items[items.indexOf(it)] = it.copy(amount = it.amount - amount)
                } else {
                    items.remove(it)
                }
            }
        } ?: throw Exception()
    }

    override fun increase(productId: Int, amount: Int) {
        items.find { it.product.id == productId }?.let {
            items[items.indexOf(it)] = it.copy(amount = it.amount + amount)
        } ?: throw Exception()
    }

    override fun clear() {
        items.clear()
        comment = ""
        profit = 0.0
    }

    override fun get(): Basket {
        return Basket(
            items = this.items.toList(),
            price = calculate() + profit,
            comment = comment
        )
    }

    private fun calculate(): Double {
        return items.sumOf { it.product.price * it.amount }
    }

    override fun comment(comment: String) {
        this.comment = comment
    }

    override fun setPrice(price: Double) {
        profit =  price - calculate()
    }

    companion object {
        fun create(): BasketDataSource {
            return InMemoryBasketDataSource()
        }
    }
}