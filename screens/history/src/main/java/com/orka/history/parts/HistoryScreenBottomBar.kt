package com.orka.history.parts

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.res.Drawables
import com.orka.res.Strings
import com.orka.ui.BottomBar
import com.orka.ui.NavItem

@Composable
internal fun HistoryScreenBottomBar(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    navigateToBasket: () -> Unit,
    reloadScreen: () -> Unit
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
            selected = false,
            description = stringResource(Strings.basket),
            painter = painterResource(Drawables.shopping_cart_outlined),
            click = navigateToBasket
        ),
        NavItem(
            name = stringResource(Strings.history),
            selected = true,
            description = stringResource(Strings.history),
            painter = painterResource(Drawables.history),
            click = reloadScreen
        )
    )
    BottomBar(modifier = modifier, navItems)
}