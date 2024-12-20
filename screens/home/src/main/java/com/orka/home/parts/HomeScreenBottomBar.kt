package com.orka.home.parts

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.res.Drawables
import com.orka.res.Strings
import com.orka.ui.BottomBar
import com.orka.ui.NavItem

@Composable
internal fun HomeScreenBottomBar(
    reloadScreen: () -> Unit,
    navigateToHistory: () -> Unit
) {

    val navItems = listOf(
        NavItem(
            name = stringResource(id = Strings.home),
            selected = true,
            description = stringResource(Strings.home),
            icon = painterResource(Drawables.home_filled),
            click = reloadScreen
        ),
        NavItem(
            name = stringResource(Strings.history),
            selected = false,
            description = stringResource(Strings.history),
            icon = painterResource(Drawables.history),
            click = navigateToHistory
        )
    )
    BottomBar(navItems = navItems)
}