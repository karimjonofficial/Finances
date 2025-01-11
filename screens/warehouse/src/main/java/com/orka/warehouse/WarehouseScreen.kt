package com.orka.warehouse

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.orka.core.Formatter
import com.orka.warehouse.parts.AddProductDialog
import com.orka.warehouse.parts.ReceiveDialog
import com.orka.components.AppScaffold
import com.orka.input.CurrencyVisualTransformation
import com.orka.products.Product
import com.orka.stock.StockItem
import com.orka.warehouse.parts.WarehouseScreenTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WarehouseScreen(
    modifier: Modifier = Modifier,
    formatter: Formatter,
    productsContentState: ProductsContentStates,
    stockContentState: StockContentStates,
    initializeProductsContent: ProductsContentStates.Initial.() -> Unit,
    initializeStockItemsContent: StockContentStates.Initial.() -> Unit,
    processProductsContent: ProductsContentStates.Processing.() -> Unit,
    processStockContent: StockContentStates.Processing.() -> Unit,
    refreshStockContent: StockContentStates.Empty.() -> Unit,
    retryStockContent: StockContentStates.Failure.() -> Unit,
    refreshProductContent: ProductsContentStates.Empty.() -> Unit,
    retryProductContent: ProductsContentStates.Failure.() -> Unit,
    selectProduct: ProductsContentStates.Success.(Product) -> Unit,
    addToBasket: StockContentStates.Success.(StockItem) -> Unit,
    addReceive: StockContentStates.(Int, Int, Double, String) -> Unit,
    addProduct: ProductsContentStates.(String, Double, String) -> Unit,
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
                onSuccess = { name, price, description ->
                    productsContentState.addProduct(name, price, description)
                    productsDialogVisible.value = false
                },
                currencyVisualTransformation = currencyVisualTransformation
            )
        }

        WarehouseScreenContent(
            modifier = Modifier.padding(innerPadding),
            productsContentState = productsContentState,
            stockContentState = stockContentState,
            formatter = formatter,
            initializeProductsContent = initializeProductsContent,
            initializeStockItemsContent = initializeStockItemsContent,
            processProductsContent = processProductsContent,
            processStockContent = processStockContent,
            selectProduct = selectProduct,
            addToBasket = addToBasket,
            refreshStockContent = refreshStockContent,
            retryStockContent = retryStockContent,
            retryProductContent = retryProductContent,
            refreshProductContent = refreshProductContent
        )
    }
}