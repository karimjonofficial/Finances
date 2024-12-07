package com.orka.finances.features.products.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.orka.finances.features.products.data.sources.local.InMemoryProductsDataSource
import com.orka.finances.features.products.presentation.viewmodels.ProductsScreenViewModel
import com.orka.finances.lib.data.credentials.Credentials
import com.orka.finances.lib.data.credentials.local.LocalCredentialsDataSource

@Composable
fun ProductsScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductsScreenViewModel
) {
    val uiState = viewModel.uiState.collectAsState()


}

@Preview
@Composable
private fun ProductsScreenPreview() {
    val categoryId = 1
    val productsDataSource = InMemoryProductsDataSource()
    val credentialsDataSource = LocalCredentialsDataSource(Credentials("token", "refresh"))
    val productsScreenViewModel = ProductsScreenViewModel(categoryId, productsDataSource, credentialsDataSource)
    Scaffold { innerPadding ->
        ProductsScreen(
            modifier = Modifier.padding(innerPadding),
            viewModel = productsScreenViewModel
        )
    }
}