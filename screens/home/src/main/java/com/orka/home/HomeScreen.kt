package com.orka.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.orka.home.parts.AddCategoryDialog
import com.orka.home.parts.HomeScreenBottomBar
import com.orka.home.parts.HomeScreenFloatingActionButton
import com.orka.home.parts.HomeScreenTopBar
import com.orka.ui.AppScaffold
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel,
    navigateToHistory: () -> Unit,
    navigateToBasket: () -> Unit
) {
    val dialogVisible = rememberSaveable { mutableStateOf(false) }
    val lazyGridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    AppScaffold(
        topBar = { HomeScreenTopBar(scrollBehavior) {} },
        bottomBar = { HomeScreenBottomBar(
            reloadScreen = {
                viewModel.fetch()
                coroutineScope.launch {
                    lazyGridState.animateScrollToItem(0, 0)
                }
            },
            navigateToHistory = navigateToHistory,
            navigateToBasket = navigateToBasket
        ) },
        floatingActionButton = { HomeScreenFloatingActionButton {
            dialogVisible.value = true
        } },
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
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

