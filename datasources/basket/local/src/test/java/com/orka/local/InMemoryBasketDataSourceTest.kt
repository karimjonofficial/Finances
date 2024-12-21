package com.orka.local

import com.orka.basket.BasketItem
import com.orka.products.Product
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class InMemoryBasketDataSourceTest {
    private val dataSource = InMemoryBasketDataSource()

    @Test
    fun `New source returns empty basket`() {
        val basket = dataSource.get()
        assertEquals("", basket.comment)
        assertEquals(0.0, basket.price)
        assertEquals(emptyList<BasketItem>(), basket.items)
    }

    @Test
    fun `After comment returns basket with comment`() {
        val comment = "Comment"
        dataSource.comment(comment)
        assertEquals(comment, dataSource.get().comment)
    }

    @Test
    fun `When amount is negative, does not add an item into basket`() {
        val item = BasketItem(
            product = Product(1, "Product", 1000.0, "", 1),
            amount = 0
        )

        assertThrows<Exception> { dataSource.add(item) }
        assertTrue(dataSource.get().items.isEmpty())
    }

    @Test
    fun `Throws exception when removing the basket is empty`() {
        assertThrows<Exception> { dataSource.remove(1) }
    }

    @Test
    fun `Throws if basket is empty`() {
        assertThrows<Exception> { dataSource.decrease(1, 1) }
    }

    @Test
    fun `Throws if decrease amount is less than one`() {
        assertThrows<Exception> { dataSource.decrease(1, 0) }
    }

    @Nested
    inner class BasketItemContext {
        private val item = BasketItem(
            product = Product(1, "Product", 1000.0, "", 1),
            amount = 1
        )

        @Test
        fun `Adds an item into basket`() {
            dataSource.add(item)
            assertTrue(dataSource.get().items.size == 1)
        }

        @Test
        fun `Duplicate items increase amount`() {
            dataSource.add(item)
            dataSource.add(item)
            assertTrue(dataSource.get().items.size == 1)
            assertTrue(dataSource.get().items[0].amount == 2)
        }

        @Test
        fun `Increases amount`() {
            val amount = 1
            dataSource.add(item)
            val oldAmount = dataSource.get().items[0].amount
            dataSource.increase(item.product.id, amount)
            assertEquals(amount + oldAmount, dataSource.get().items[0].amount)
        }

        @Test
        fun `Throws when increasing product has not been added`() {
            val amount = 1
            dataSource.add(item)
            assertThrows<Exception> { dataSource.increase(2, amount) }
        }

        @Test
        fun `Removes item`() {
            dataSource.add(item)
            assertEquals(1, dataSource.get().items.size)
            dataSource.remove(item.product.id)
            assertEquals(0, dataSource.get().items.size)
        }

        @Test
        fun `Throws exception when item being removed does not exist`() {
            dataSource.add(item)
            assertEquals(item, dataSource.get().items[0])
            assertThrows<Exception> { dataSource.remove(item.product.id + 1) }
        }

        @Test
        fun `Throws exception when item being decreased does not exist`() {
            dataSource.add(item)
            assertEquals(item, dataSource.get().items[0])
            assertThrows<Exception> {
                dataSource.decrease(dataSource.get().items[0].product.id + 1, 1)
            }
        }

        @Test
        fun `Throws exception when item being decreased has no such many amount`() {
            dataSource.add(item)
            assertEquals(item, dataSource.get().items[0])
            assertThrows<Exception> {
                dataSource.decrease(dataSource.get().items[0].product.id, 2)
            }
        }

        @Test
        fun `Removes if basket item has amount zero`() {
            dataSource.add(item)
            dataSource.decrease(item.product.id, 1)
            assertEquals(0, dataSource.get().items.size)
        }

        @Test
        fun `Decreases amount of item`() {
            dataSource.add(item)
            dataSource.add(item)
            dataSource.decrease(dataSource.get().items[0].product.id, 1)
            assertEquals(1, dataSource.get().items[0].amount)
        }

        @Test
        fun `Cleans the basket up`() {
            dataSource.add(item)
            dataSource.comment("Some comment")
            dataSource.clear()
            assertEquals(emptyList<BasketItem>(), dataSource.get().items)
            assertEquals("", dataSource.get().comment)
        }
    }
}