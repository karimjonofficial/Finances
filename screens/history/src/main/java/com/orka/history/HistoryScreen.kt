package com.orka.history

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.orka.core.Formatter
import com.orka.history.parts.HistoryScreenBottomBar
import com.orka.history.parts.HistoryScreenContent
import com.orka.history.parts.HistoryScreenTopBar
import com.orka.ui.AppScaffold
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
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val lazyListState = rememberLazyListState()

    AppScaffold(
        topBar = { HistoryScreenTopBar(scrollBehavior = scrollBehavior) },
        bottomBar = {
            HistoryScreenBottomBar(
                navigateToHome = navigateToHome,
                navigateToBasket = navigateToBasket,
                reloadScreen = {
                    viewModel.fetch()
                    coroutineScope.launch {
                        lazyListState.animateScrollToItem(0, 0)
                    }
                }
            )
        },
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->

        viewModel.fetch()
        val uiState = viewModel.uiState.collectAsState()

        HistoryScreenContent(
            modifier = Modifier.padding(innerPadding),
            items = uiState.value,
            lazyListState = lazyListState,
            formatter = formatter
        )
    }
}