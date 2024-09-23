package com.orka.finances.features.home.data.sources

import com.orka.finances.features.home.data.sources.local.CategoriesInMemoryDataSource
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CategoriesInMemoryDataSourceTest {
    private lateinit var source: CategoriesInMemoryDataSource

    @Before
    fun setUp() {
        source = CategoriesInMemoryDataSource()
    }

    @Test
    fun newSourceShouldReturnEmptyList() {
        val categories = source.getCategories().first
        assert(categories.isEmpty())
    }

    @Test
    fun addCategoryShouldReturnCategoryName() {
        val expected = "New Category"
        val actual = source.addCategory(expected).first
        assertEquals(expected, actual)
    }

    @Test
    fun addCategoryShouldAddNewCategory() {
        val name = "New Category"
        source.addCategory(name)

        assert(source.getCategories().first.isNotEmpty())
    }

    @Test
    fun shouldLoadData() {
        source.loadInitialData()
        assertEquals(3, source.getCategories().first.size)
    }
}

