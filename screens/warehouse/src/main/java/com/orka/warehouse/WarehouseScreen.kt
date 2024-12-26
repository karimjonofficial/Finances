package com.orka.warehouse

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.orka.core.Formatter
import com.orka.products.ProductsScreenViewModel
import com.orka.warehouse.parts.AddProductDialog
import com.orka.stock.StockScreenViewModel
import com.orka.warehouse.parts.AddReceiveDialog
import com.orka.components.AppScaffold
import com.orka.warehouse.parts.WarehouseScreenTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WarehouseScreen(
    modifier: Modifier = Modifier,
    stockScreenViewModel: StockScreenViewModel,
    productsScreenViewModel: ProductsScreenViewModel,
    formatter: Formatter
) {

    val receiveDialogVisible = rememberSaveable { mutableStateOf(false) }
    val productsDialogVisible = rememberSaveable { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    AppScaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            WarehouseScreenTopBar(
                addClick = { productsDialogVisible.value = true },
                importClick = { receiveDialogVisible.value = true },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->

        if (receiveDialogVisible.value) {
            val state = stockScreenViewModel.productsState.collectAsState()

            AddReceiveDialog(
                dismissRequest = { receiveDialogVisible.value = false },
                addReceive = { product, amount, price, comment ->
                    stockScreenViewModel.receive(product.id, amount, price, comment)
                    receiveDialogVisible.value = false
                    stockScreenViewModel.fetch()
                },
                products = state.value
            )
        }

        if (productsDialogVisible.value) {
            AddProductDialog(
                dismissRequest = { productsDialogVisible.value = false },
                addClick = { name, price, description ->
                    productsScreenViewModel.add(name, price, description)
                    productsDialogVisible.value = false
                    productsScreenViewModel.fetch()
                }
            )
        }

        WarehouseScreenContent(
            modifier = Modifier.padding(innerPadding),
            stockScreenViewModel = stockScreenViewModel,
            productsScreenViewModel = productsScreenViewModel,
            formatter = formatter
        )
    }
}