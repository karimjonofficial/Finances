package com.orka.basket

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.orka.basket.parts.BasketScreenBottomBar
import com.orka.basket.parts.BasketScreenTopBar
import com.orka.core.Formatter
import com.orka.components.AppScaffold
import com.orka.core.Printer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasketScreen(
    modifier: Modifier = Modifier,
    viewModel: BasketScreenViewModel,
    navigateToHome: () -> Unit,
    navigateToHistory: () -> Unit,
    formatter: Formatter,
    printer: Printer
) {

    viewModel.fetch()

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val lazyListState = rememberLazyListState()

    AppScaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { BasketScreenTopBar(scrollBehavior) { viewModel.clear() } },
        bottomBar = {
            BasketScreenBottomBar(
                reloadScreen = {
                    coroutineScope.launch {
                        lazyListState.animateScrollToItem(0, 0)
                    }
                },
                navigateToHome = navigateToHome,
                navigateToHistory = navigateToHistory
            )
        }
    ) { innerPadding ->

        BasketScreenContent(
            modifier = Modifier.padding(innerPadding),
            viewModel = viewModel,
            lazyListState = lazyListState,
            formatter = formatter,
            printer = printer
        )
    }
}