package com.orka.basket.parts

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.components.NavigationBar
import com.orka.items.NavItem
import com.orka.res.Drawables
import com.orka.res.Strings

@Composable
internal fun BasketScreenBottomBar(
    modifier: Modifier = Modifier,
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

    NavigationBar(
        modifier = modifier,
        navItems = navItems
    )
}