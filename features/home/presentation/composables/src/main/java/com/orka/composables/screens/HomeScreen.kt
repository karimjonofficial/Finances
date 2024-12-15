package com.orka.composables.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.orka.composables.screens.parts.AddCategoryDialog
import com.orka.composables.screens.parts.HomeScreenFloatingActionButton
import com.orka.composables.screens.parts.HomeScreenTopBar
import com.orka.ui.AppScaffold
import com.orka.ui.NavItem
import com.orka.ui.BottomBar
import com.orka.viewmodels.HomeScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel,
    navigateToProductsScreen: () -> Unit
) {
    val dialogVisible = rememberSaveable { mutableStateOf(false) }
    val lazyGridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()

    val navItems = listOf(
        NavItem("Home", true, "Home", Icons.Filled.Home) {
            viewModel.fetchData()
            coroutineScope.launch {
                lazyGridState.animateScrollToItem(0, lazyGridState.firstVisibleItemScrollOffset)
            }
        },
        NavItem("Products", false, "Products", Icons.AutoMirrored.Outlined.List) {
            navigateToProductsScreen()
        }
    )

    AppScaffold(
        topBar = { HomeScreenTopBar() },
        bottomBar = { BottomBar(navItems = navItems) },
        floatingActionButton = {
            HomeScreenFloatingActionButton { dialogVisible.value = true }
        },
        modifier = modifier,
    ) { innerPadding ->

        if (dialogVisible.value) {
            AddCategoryDialog(
                modifier = Modifier.fillMaxWidth(),
                dismissRequest = { dialogVisible.value = false },
                addClick = { name, description ->
                    viewModel.addCategory(name, description)
                    dialogVisible.value = false
                }
            )
        }

        HomeContent(
            modifier = Modifier.padding(innerPadding),
            viewModel = viewModel,
            lazyGridState = lazyGridState
        )
    }
}