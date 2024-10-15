package com.orka.finances.features.home.presentation.viewmodels

import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.data.sources.local.CategoriesInMemoryDataSource
import com.orka.finances.features.home.data.sources.local.categoriesList
import org.junit.Assert.assertEquals
import org.junit.Test

class HomeScreenViewModelTest {
    private fun createViewModel(dataSource: CategoriesDataSource) = HomeScreenViewModel(dataSource)

    @Test
    fun shouldAddNewCategory() {
        val dataSource: CategoriesDataSource = DummyCategoriesDataSource()
        val viewModel = createViewModel(dataSource)
        val expected = "New Category"

        viewModel.addCategory(expected)

        assertEquals(expected, viewModel.categories.value.last().name)
    }

    @Test
    fun loadsDataFromDataSource() {
        val dataSource = CategoriesInMemoryDataSource()
        dataSource.loadInitialData()
        val viewModel = createViewModel(dataSource)

        assertEquals(categoriesList.size, viewModel.categories.value.size)
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