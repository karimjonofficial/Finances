package com.orka.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.orka.components.AppScaffold
import com.orka.home.parts.AddCategoryDialog
import com.orka.home.parts.HomeScreenBottomBar
import com.orka.home.parts.HomeScreenFloatingActionButton
import com.orka.home.parts.HomeScreenTopBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel,
    navigateToHistory: () -> Unit,
    navigateToBasket: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()
    val dialogVisible = rememberSaveable { mutableStateOf(false) }
    val lazyGridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope { Dispatchers.Main }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    AppScaffold(
        topBar = { HomeScreenTopBar(scrollBehavior = scrollBehavior) {} },
        bottomBar = {
            HomeScreenBottomBar(
                reloadScreen = {
                    coroutineScope.launch {
                        lazyGridState.animateScrollToItem(0, 0)
                        viewModel.reset()
                    }
                },
                navigateToHistory = navigateToHistory,
                navigateToBasket = navigateToBasket
            )
        },
        floatingActionButton = {
            HomeScreenFloatingActionButton {
                dialogVisible.value = true
            }
        },
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

        HomeScreenContent(
            modifier = Modifier.padding(innerPadding),
            viewModel = viewModel,
            lazyGridState = lazyGridState,
            state = uiState.value
        )
    }
}

