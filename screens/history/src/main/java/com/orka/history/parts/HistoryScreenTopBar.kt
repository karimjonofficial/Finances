package com.orka.history.parts

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.res.Strings
import com.orka.ui.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HistoryScreenTopBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    TopBar(
        modifier = modifier,
        title = stringResource(Strings.history),
        scrollBehavior = scrollBehavior
    )
}