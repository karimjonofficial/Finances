package com.orka.finances.features.home.entities

import org.junit.Assert.assertEquals
import org.junit.Test

class CategoryTest {
    companion object {
        private const val ID = 1
        private const val NAME = "Category"
        val category = Category(ID, NAME)
        val dto = CategoryDto(ID, NAME)
    }

    @Test
    fun returnsCorrectDto() {
        val actual = category.getData()
        assertEquals(dto, actual)
    }
    
    @Test
    fun renamesCorrectly() {
        val expected = "MyCategory"
        category.rename(expected)
        val actual = category.getData().name

        assertEquals(expected, actual)
    }
}