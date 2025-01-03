package com.orka.components

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.items.NavItem

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navItems: List<NavItem>
) {
    BottomAppBar(modifier = modifier) {
        for (i in navItems) {
            NavigationBarItem(
                selected = i.selected,
                onClick = { i.click() },
                icon = {
                    Icon(
                        painter = i.painter,
                        contentDescription = i.description
                    )
                }
            )
        }
    }
}