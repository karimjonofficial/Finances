package com.orka.history

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.orka.components.AppScaffold
import com.orka.core.Formatter
import com.orka.history.parts.HistoryScreenBottomBar
import com.orka.history.parts.HistoryScreenTopBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryScreenViewModel,
    navigateToHome: () -> Unit,
    navigateToBasket: () -> Unit,
    formatter: Formatter
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    viewModel.fetch()
    val coroutineScope = rememberCoroutineScope { Dispatchers.Main }
    val saleListState = rememberLazyListState()
    val receiveListState = rememberLazyListState()

    AppScaffold(
        topBar = { HistoryScreenTopBar(scrollBehavior = scrollBehavior) },
        bottomBar = {
            HistoryScreenBottomBar(
                navigateToHome = navigateToHome,
                navigateToBasket = navigateToBasket,
                reloadScreen = {
                    viewModel.fetch()
                    coroutineScope.launch {
                        receiveListState.animateScrollToItem(0, 0)
                        saleListState.animateScrollToItem(0, 0)
                    }
                }
            )
        },
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->

        HistoryScreenContent(
            modifier = Modifier.padding(innerPadding),
            viewModel = viewModel,
            receiveListState = receiveListState,
            saleListState = saleListState,
            formatter = formatter
        )
    }
}