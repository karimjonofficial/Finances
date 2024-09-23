package com.orka.finances.features.home.presentation.viewmodels

import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.data.sources.local.CategoriesInMemoryDataSource
import com.orka.finances.lib.errors.data.sources.DataSourceError
import com.orka.finances.lib.errors.data.sources.NullDataSourceError
import com.orka.finances.lib.errors.data.sources.UnknownDataSourceError
import org.junit.Assert.assertEquals
import org.junit.Test

class HomeScreenViewModelTest {
    private fun createViewModel(dataSource: CategoriesDataSource) = HomeScreenViewModel(dataSource)

    @Test
    fun shouldAddNewCategory() {
        val dataSource = DummyCategoriesDataSource()
        val viewModel = createViewModel(dataSource)
        val expected = "New Category"

        viewModel.addCategory(expected)

        assertEquals(expected, viewModel.categories.value.last())
    }

    @Test
    fun loadsDataFromDataSource() {
        val dataSource = CategoriesInMemoryDataSource()
        dataSource.loadInitialData()
        val viewModel = createViewModel(dataSource)

        assertEquals(3, viewModel.categories.value.size)
    }

    @Test
    fun shouldNotAddIfErrorIsReturnedByDataSource() {
        val dataSource = CategoriesDataSourceStub()
        val viewModel = createViewModel(dataSource)
        val count = viewModel.categories.value.size

        viewModel.addCategory("New Category")

        assertEquals(count, viewModel.categories.value.size)
    }

    @Test
    fun shouldCallDataSourceAddFunction() {
        val dataSource = CategoriesDataSourceSpy()
        val viewModel = createViewModel(dataSource)

        viewModel.addCategory("New Category")

        assertEquals(true, dataSource.isCalled())
    }
}

private class CategoriesDataSourceStub : CategoriesDataSource {
    override fun getCategories(): Pair<List<String>, DataSourceError> {
        return Pair(emptyList(), NullDataSourceError)
    }

    override fun addCategory(name: String): Pair<String, DataSourceError> {
        return Pair("", UnknownDataSourceError())
    }

}

private class CategoriesDataSourceSpy : CategoriesDataSource {
    var called = false

    override fun getCategories(): Pair<List<String>, DataSourceError> {
        return Pair(emptyList(), NullDataSourceError)
    }

    override fun addCategory(name: String): Pair<String, DataSourceError> {
        called = true
        return Pair(name, NullDataSourceError)
    }

    fun isCalled(): Boolean {
        return called
    }
}

private class DummyCategoriesDataSource : CategoriesDataSource {
    override fun getCategories(): Pair<List<String>, DataSourceError> {
        return Pair(emptyList(), NullDataSourceError)
    }

    override fun addCategory(name: String): Pair<String, DataSourceError> {
        return Pair(name, NullDataSourceError)
    }

}