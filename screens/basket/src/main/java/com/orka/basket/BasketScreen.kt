package com.orka.basket

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.basket.parts.BasketScreenBottomBar
import com.orka.basket.parts.BasketScreenContent
import com.orka.res.Drawables
import com.orka.res.Strings
import com.orka.ui.AppScaffold
import com.orka.ui.TopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasketScreen(
    modifier: Modifier = Modifier,
    viewModel: BasketScreenViewModel,
    navigateToHome: () -> Unit,
    navigateToHistory: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val lazyListState = rememberLazyListState()

    AppScaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                title = stringResource(Strings.basket),
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(Drawables.delete_outlined),
                            contentDescription = stringResource(Strings.clear)
                        )
                    }
                }
            )
        },
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

        val uiState = viewModel.uiState.collectAsState()

        BasketScreenContent(
            modifier = Modifier.padding(innerPadding),
            basket = uiState.value,
            lazyListState = lazyListState
        )
    }
}