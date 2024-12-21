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
    reloadScreen: () -> Unit
) {

    val navItems = listOf(
        NavItem(
            name = stringResource(Strings.home),
            selected = false,
            description = stringResource(Strings.home),
            icon = painterResource(Drawables.home_outlined),
            click = navigateToHome
        ),
        NavItem(
            name = stringResource(Strings.history),
            selected = true,
            description = stringResource(Strings.history),
            icon = painterResource(Drawables.history),
            click = reloadScreen
        )
    )

    BottomBar(modifier = modifier, navItems)
}