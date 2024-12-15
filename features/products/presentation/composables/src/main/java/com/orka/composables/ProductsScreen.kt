package com.orka.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.orka.ui.AppScaffold
import com.orka.ui.NavItem
import com.orka.ui.BottomBar
import com.orka.viewmodels.ProductsScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun ProductsScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductsScreenViewModel,
    navigateToHome: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()

    viewModel.fetch()

    val navItems = listOf(
        NavItem("Home", false, "Home", Icons.Outlined.Home) {
            navigateToHome()
        },
        NavItem("Products", true, "Products", Icons.AutoMirrored.Filled.List) {
            viewModel.fetch()
            coroutineScope.launch {
                lazyListState.animateScrollToItem(0)
            }
        }
    )

    AppScaffold(
        topBar = { ProductsScreenTopBar() },
        bottomBar = { BottomBar(navItems = navItems) },
        modifier = modifier,
    ) { innerPadding ->

        ProductsContent(
            modifier = Modifier.padding(innerPadding),
            viewModel = viewModel,
            lazyListState = lazyListState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProductsScreenTopBar() {
    TopAppBar(
        title = { Text("Finances") }
    )
}

@Preview
@Composable
private fun ProductsScreenPreview() {
    val viewModel = ProductsScreenViewModel(0, InMemoryProductsDataSource()) {}
    viewModel.fetch()
    Surface {
        ProductsScreen(viewModel = viewModel) {}
    }
}