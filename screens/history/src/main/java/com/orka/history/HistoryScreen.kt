package com.orka.history

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.receive.Receive
import com.orka.res.Drawables
import com.orka.res.Strings
import com.orka.ui.AppScaffold
import com.orka.ui.BottomBar
import com.orka.ui.NavItem
import com.orka.ui.TopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryScreenViewModel,
    navigateToHome: () -> Unit,
    formatCurrency: (Double) -> String
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val lazyListState = rememberLazyListState()
    val uiState = viewModel.uiState.collectAsState()
    viewModel.fetch()

    AppScaffold(
        topBar = { HistoryScreenTopBar() },
        bottomBar = {
            HistoryScreenBottomBar(
                navigateToHome = navigateToHome,
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

        Surface(modifier = modifier.padding(innerPadding)) {

            HistoryScreenContent(
                items = uiState.value,
                lazyListState = lazyListState,
                formatCurrency = formatCurrency
            )
        }
    }
}

@Composable
fun HistoryScreenContent(
    modifier: Modifier = Modifier,
    items: List<Receive>,
    lazyListState: LazyListState,
    formatCurrency: (Double) -> String
) {
    HistoryList(
        modifier = modifier,
        items = items,
        state = lazyListState,
        formatCurrency = formatCurrency
    )
}

@Composable
fun HistoryList(
    modifier: Modifier = Modifier,
    items: List<Receive>,
    state: LazyListState,
    formatCurrency: (Double) -> String
) {

    LazyColumn(
        modifier = modifier,
        state = state
    ) {
        items(items) {
            ReceiveCard(item = it, formatCurrency = formatCurrency)
        }
    }
}

@Composable
fun ReceiveCard(item: Receive, formatCurrency: (Double) -> String) {
    ListItem(
        overlineContent = { Text(item.id.toString()) },
        headlineContent = { Text(formatCurrency(item.price)) },
        supportingContent = { Text(item.comment) }
    )
}

@Composable
fun HistoryScreenBottomBar(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    reloadScreen: () -> Unit
) {

    val navItems = listOf(
        NavItem(
            name = stringResource(Strings.home),
            selected = false,
            description = stringResource(Strings.home),
            icon = painterResource(Drawables.home_outlined),
            click = navigateToHome
        ),
        NavItem(
            name = stringResource(Strings.history),
            selected = true,
            description = stringResource(Strings.history),
            icon = painterResource(Drawables.history),
            click = reloadScreen
        )
    )

    BottomBar(modifier = modifier, navItems)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreenTopBar(modifier: Modifier = Modifier) {
    TopBar(
        modifier = modifier,
        title = stringResource(Strings.history)
    )
}
