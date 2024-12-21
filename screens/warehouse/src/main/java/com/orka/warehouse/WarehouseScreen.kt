package com.orka.warehouse

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.products.ProductsContent
import com.orka.products.ProductsScreenViewModel
import com.orka.products.parts.AddProductDialog
import com.orka.res.Drawables
import com.orka.res.Strings
import com.orka.stock.StockContent
import com.orka.stock.StockScreenViewModel
import com.orka.stock.parts.AddReceiveDialog
import com.orka.ui.AppScaffold
import com.orka.ui.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WarehouseScreen(
    modifier: Modifier = Modifier,
    stockScreenViewModel: StockScreenViewModel,
    productsScreenViewModel: ProductsScreenViewModel,
    formatCurrency: (Double) -> String
) {

    val receiveDialogVisible = rememberSaveable { mutableStateOf(false) }
    val productsDialogVisible = rememberSaveable { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    stockScreenViewModel.fetchProducts()
    stockScreenViewModel.fetch()
    productsScreenViewModel.fetch()

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
            val state = stockScreenViewModel.dialogState.collectAsState()

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
            formatCurrency = formatCurrency
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WarehouseScreenTopBar(
    addClick: () -> Unit,
    importClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopBar(
        title = stringResource(Strings.warehouse),
        actions = {

            IconButton(onClick = addClick) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add"
                )
            }

            IconButton(onClick = importClick) {
                Icon(
                    painter = painterResource(Drawables.download),
                    contentDescription = stringResource(Strings.receive_new_products)
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun WarehouseScreenContent(
    modifier: Modifier,
    stockScreenViewModel: StockScreenViewModel,
    productsScreenViewModel: ProductsScreenViewModel,
    formatCurrency: (Double) -> String
) {
    val tabIndex = rememberSaveable { mutableIntStateOf(0) }

    Column(modifier = modifier) {

        PrimaryTabRow(
            selectedTabIndex = tabIndex.intValue,
        ) {
            Tab(
                selected = tabIndex.intValue == 0,
                onClick = { tabIndex.intValue = 0 },
                text = { Text(stringResource(Strings.warehouse)) }
            )

            Tab(
                selected = tabIndex.intValue == 1,
                onClick = { tabIndex.intValue = 1 },
                text = { Text(stringResource(Strings.products)) }
            )
        }

        when (tabIndex.intValue) {
            0 -> {
                StockContent(viewModel = stockScreenViewModel)
                stockScreenViewModel.fetchProducts()
                stockScreenViewModel.fetch()
            }

            1 -> {
                ProductsContent(
                    viewModel = productsScreenViewModel,
                    formatCurrency = formatCurrency
                )
                productsScreenViewModel.fetch()
            }
        }
    }
}