package com.orka.home.parts

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.components.NavigationBar
import com.orka.items.NavItem
import com.orka.res.Drawables
import com.orka.res.Strings

@Composable
internal fun HomeScreenBottomBar(
    modifier: Modifier = Modifier,
    reloadScreen: () -> Unit,
    navigateToHistory: () -> Unit,
    navigateToBasket: () -> Unit
) {

    val navItems = listOf(
        NavItem(
            name = stringResource(id = Strings.home),
            selected = true,
            description = stringResource(Strings.home),
            painter = painterResource(Drawables.home_filled),
            click = reloadScreen
        ),
        NavItem(
            name = stringResource(Strings.basket),
            selected = false,
            description = stringResource(Strings.basket),
            painter = painterResource(Drawables.shopping_cart_outlined),
            click = navigateToBasket
        ),
        NavItem(
            name = stringResource(Strings.history),
            selected = false,
            description = stringResource(Strings.history),
            painter = painterResource(Drawables.history),
            click = navigateToHistory
        )
    )
    NavigationBar(modifier, navItems)
}