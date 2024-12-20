package com.orka.products

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.orka.core.HttpServiceImpl
import com.orka.products.fixtures.InMemoryProductsDataSource
import com.orka.products.fixtures.UnauthorizerImpl
import com.orka.products.parts.AddProductDialog
import com.orka.products.parts.ProductsScreenFloatingActionButton
import com.orka.products.parts.ProductsScreenTopBar
import com.orka.res.Drawables
import com.orka.ui.AppScaffold
import com.orka.ui.BottomBar
import com.orka.ui.NavItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductsScreenViewModel,
    navigateToHome: () -> Unit
) {
    val lazyListState = rememberLazyListState()
    val dialogVisible = rememberSaveable { mutableStateOf(false) }

    if(dialogVisible.value) {
        AddProductDialog(
            dismissRequest = { dialogVisible.value = false },
            addClick = { name, price, description ->
                viewModel.add(name, price, description)
                dialogVisible.value = false
            }
        )
    } else {
        viewModel.fetch()
    }

    val navItems = listOf(
        NavItem("Home", false, "Home", painterResource(Drawables.home_outlined)) {
            navigateToHome()
        }
    )
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    AppScaffold(
        topBar = { ProductsScreenTopBar(scrollBehavior) },
        bottomBar = { BottomBar(navItems = navItems) },
        floatingActionButton = { ProductsScreenFloatingActionButton { dialogVisible.value = true } },
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->

        ProductsContent(
            modifier = Modifier.padding(innerPadding),
            viewModel = viewModel,
            lazyListState = lazyListState,
            formatCurrency = { it.toString() }
        )
    }
}

@Preview
@Composable
private fun ProductsScreenPreview() {
    val viewModel = ProductsScreenViewModel(0, InMemoryProductsDataSource(), HttpServiceImpl(
        UnauthorizerImpl()
    ))
    viewModel.fetch()
    Surface {
        ProductsScreen(viewModel = viewModel) {}
    }
}