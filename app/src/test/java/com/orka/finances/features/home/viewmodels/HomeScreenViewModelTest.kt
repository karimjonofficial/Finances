package com.orka.finances.features.home.viewmodels

import org.junit.Assert.assertEquals
import org.junit.Test

class HomeScreenViewModelTest {
    @Test
    fun shouldAddNewCategory() {
        val viewModel = HomeScreenViewModel()
        val expected = "New Category"

        viewModel.addCategory(expected)

        assertEquals(expected, viewModel.categories.value.last())
    }
}