package com.orka.local

import com.orka.basket.Basket
import com.orka.basket.BasketItem
import com.orka.core.BasketDataSource

internal class InMemoryBasketDataSource : BasketDataSource {

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
                items.remove(it)
                if(amount < it.amount) {
                    items.add(it.copy(amount = it.amount - amount))
                }
            }
        } ?: throw Exception()
    }

    override fun increase(productId: Int, amount: Int) {
        items.find { it.product.id == productId }?.let {
            val new = it.copy(amount = it.amount + amount)
            items.remove(it)
            items.add(new)
        } ?: throw Exception()
    }

    override fun clear() {
        items.clear()
        comment = ""
    }

    override fun get(): Basket {
        return Basket(
            items = this.items.toList(),
            comment = comment
        )
    }

    override fun comment(comment: String) {
        this.comment = comment
    }
}