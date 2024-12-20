package com.orka.stock

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.orka.stock.fixtures.HttpServiceImpl
import com.orka.stock.fixtures.ProductDataSourceImpl
import com.orka.stock.fixtures.ReceiveDataSourceImpl
import com.orka.stock.fixtures.StockDataSourceImpl
import com.orka.stock.parts.AddReceiveDialog
import com.orka.stock.parts.StockScreenTopBar
import com.orka.ui.AppScaffold

@Composable
fun StockScreen(
    modifier: Modifier = Modifier,
    viewModel: StockScreenViewModel
) {
    val dialogVisible = rememberSaveable { mutableStateOf(false) }

    viewModel.fetch()
    viewModel.fetchProducts()

    AppScaffold(
        topBar = { StockScreenTopBar { dialogVisible.value = true } }
    ) { innerPadding ->

        if (dialogVisible.value) {
            val state = viewModel.dialogState.collectAsState()

            AddReceiveDialog(
                dismissRequest = { dialogVisible.value = false },
                addReceive = { productId, amount, price, comment ->
                    viewModel.receive(productId, amount, price, comment)
                    dialogVisible.value = false
                    viewModel.fetch()
                },
                products = state.value
            )
        }

        Surface(modifier = modifier.padding(innerPadding)) {
            StockContent(viewModel = viewModel)
        }
    }
}

@Preview
@Composable
private fun ProductsScreenPreview() {
    val viewModel = StockScreenViewModel(
        categoryId = 1,
        httpService = HttpServiceImpl(),
        stockDataSource = StockDataSourceImpl(),
        receiveDataSource = ReceiveDataSourceImpl(),
        productsDataSource = ProductDataSourceImpl(),
    ) {}

    viewModel.fetch()

    Scaffold { innerPadding ->
        StockContent(
            modifier = Modifier.padding(innerPadding),
            viewModel = viewModel
        )
    }
}