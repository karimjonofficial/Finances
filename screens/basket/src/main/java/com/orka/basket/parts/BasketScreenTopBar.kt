package com.orka.basket.parts

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.components.TopBar
import com.orka.res.Drawables
import com.orka.res.Strings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BasketScreenTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    clearClick: () -> Unit
) {
    TopBar(
        title = stringResource(Strings.basket),
        scrollBehavior = scrollBehavior,
        actions = {
            IconButton(onClick = clearClick) {
                Icon(
                    painter = painterResource(Drawables.delete_outlined),
                    contentDescription = stringResource(Strings.clear)
                )
            }
        }
    )
}