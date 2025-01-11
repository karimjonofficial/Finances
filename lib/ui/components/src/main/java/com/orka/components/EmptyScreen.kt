package com.orka.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.res.Strings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    refresh: () -> Unit = {}
) {
    val refreshing = rememberSaveable { mutableStateOf(false) }

    PullToRefreshBox(
        isRefreshing = refreshing.value,
        onRefresh = {
            refreshing.value = true
            refresh()
        }
    ) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(Strings.empty_list))
        }
    }
}