package com.orka.basket.parts

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.res.Drawables
import com.orka.res.Strings
import com.orka.components.BottomBar
import com.orka.items.NavItem

@Composable
internal fun BasketScreenBottomBar(
    reloadScreen: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToHistory: () -> Unit,
) {

    val navItems = listOf(
        NavItem(
            name = stringResource(Strings.home),
            selected = false,
            description = stringResource(Strings.home),
            painter = painterResource(Drawables.home_outlined),
            click = navigateToHome
        ),
        NavItem(
            name = stringResource(Strings.basket),
            selected = true,
            description = stringResource(Strings.basket),
            painter = painterResource(Drawables.shopping_cart_filled),
            click = reloadScreen
        ),
        NavItem(
            name = stringResource(Strings.history),
            selected = false,
            description = stringResource(Strings.history),
            painter = painterResource(Drawables.history),
            click = navigateToHistory
        )
    )

    BottomBar(
        navItems = navItems
    )
}