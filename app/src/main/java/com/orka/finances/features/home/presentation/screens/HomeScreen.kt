package com.orka.finances.features.home.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.orka.finances.features.home.data.sources.local.CategoriesInMemoryDataSource
import com.orka.finances.features.home.presentation.screens.parts.AddCategoryLayout
import com.orka.finances.features.home.presentation.screens.parts.CategoriesList
import com.orka.finances.features.home.presentation.screens.parts.Title
import com.orka.finances.features.home.presentation.viewmodels.HomeScreenViewModel
import com.orka.finances.lib.components.Spacer16
import com.orka.finances.lib.components.Spacer8

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel
) {
    Column(modifier = modifier) {
        val text = rememberSaveable { mutableStateOf("") }
        val productsList = viewModel.categories.collectAsState()

        Title()
        Spacer8()
        AddCategoryLayout(
            text = text.value,
            onTextChanged = { text.value = it },
            onClick = {
                viewModel.addCategory(text.value)
                text.value = ""
            }
        )
        Spacer16()
        CategoriesList(productsList.value)
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun HomeScreenPreview() {
    val viewModel = HomeScreenViewModel(
        CategoriesInMemoryDataSource()
    )

    HomeScreen(Modifier, viewModel)
}