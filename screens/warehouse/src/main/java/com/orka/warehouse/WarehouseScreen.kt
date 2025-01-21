package com.orka.warehouse

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.orka.components.AppScaffold
import com.orka.core.Formatter
import com.orka.input.CurrencyVisualTransformation
import com.orka.warehouse.parts.AddProductDialog
import com.orka.warehouse.parts.ReceiveDialog
import com.orka.warehouse.parts.WarehouseScreenTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WarehouseScreen(
    modifier: Modifier = Modifier,
    formatter: Formatter,
    productsContentState: ProductsContentStates,
    stockContentState: StockContentStates,
    addReceive: StockContentStates.(Int, Int, Double, String) -> Unit
) {
    val receiveDialogVisible = rememberSaveable { mutableStateOf(false) }
    val productsDialogVisible = rememberSaveable { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val currencyVisualTransformation = CurrencyVisualTransformation(formatter)

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

        if (receiveDialogVisible.value && productsContentState is ProductsContentStates.Success) {

            ReceiveDialog(
                dismissRequest = { receiveDialogVisible.value = false },
                addReceive = { product, amount, price, comment ->
                    stockContentState.addReceive(product.id, amount, price, comment)
                    receiveDialogVisible.value = false
                },
                products = productsContentState.products,
                currencyVisualTransformation = currencyVisualTransformation
            )
        }

        if (productsDialogVisible.value) {

            AddProductDialog(
                dismissRequest = { productsDialogVisible.value = false },
                onSuccess = { name, price, description, amount ->
                    productsContentState.addProductAndReceive(name, price, description, amount)
                    productsDialogVisible.value = false
                },
                currencyVisualTransformation = currencyVisualTransformation,
                formatter = formatter
            )
        }

        WarehouseScreenContent(
            modifier = Modifier.padding(innerPadding),
            productsContentState = productsContentState,
            stockContentState = stockContentState,
            formatter = formatter
        )
    }
}