package com.orka.finances.features.home.usecases

import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.entities.Category
import com.orka.finances.lib.errors.data.DataSourceError
import com.orka.finances.lib.errors.data.NullDataSourceError
import org.junit.Assert.assertEquals
import org.junit.Test

class GetCategoriesUseCaseTest {
    @Test
    fun getCategories() {
        val dataSource = DummyCategoriesDataSource()
        val usecase = GetCategoriesUseCase(dataSource)

        val expected: List<Category> = emptyList()
        val pair = usecase.invoke()
        val actual = pair.first

        assertEquals(expected, actual)
    }
}

class DummyCategoriesDataSource : CategoriesDataSource {
    override fun getCategories(): Pair<List<Category>, DataSourceError> {
        return Pair(emptyList(), NullDataSourceError)
    }

    override fun addCategory(name: String): Pair<Category, DataSourceError> {
        TODO("Not yet implemented")
    }
}
