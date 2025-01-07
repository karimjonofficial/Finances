package com.orka.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.items.NavItem
import androidx.compose.material3.NavigationBar as MNavigationBar

@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    navItems: List<NavItem>
) {
    MNavigationBar(modifier = modifier) {
        for (i in navItems) {
            NavigationBarItem(
                selected = i.selected,
                onClick = { i.click() },
                icon = {
                    Icon(
                        painter = i.painter,
                        contentDescription = i.description
                    )
                },
                label = { Text(text = i.name) }
            )
        }
    }
}