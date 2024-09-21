package com.orka.finances.features.home.usecases

import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.entities.Category
import com.orka.finances.lib.errors.data.DataSourceError
import com.orka.finances.lib.errors.data.NullDataSourceError
import org.junit.Assert.assertEquals
import org.junit.Test

class AddCategoryUseCaseTest {
    @Test
    fun returnsWhatGiven() {
        val datasource = CategoriesDataSourceStub()
        val useCase = AddCategoryUseCase(datasource)

        val categoryName = "New category"
        val pair = useCase.invoke(categoryName)
        val actual = pair.first

        assertEquals(categoryName, actual.toDto().name)
    }
}

private class CategoriesDataSourceStub : CategoriesDataSource {
    override fun getCategories(): Pair<List<Category>, DataSourceError> {
        return Pair(emptyList(), NullDataSourceError)
    }

    override fun addCategory(name: String): Pair<Category, DataSourceError> {
        return Pair(Category(id = 0, name = name), NullDataSourceError)
    }
}