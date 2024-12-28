package com.orka.history.parts

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.orka.components.TopBar
import com.orka.res.Strings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HistoryScreenTopBar(
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    TopBar(
        title = stringResource(Strings.history),
        scrollBehavior = scrollBehavior
    )
}