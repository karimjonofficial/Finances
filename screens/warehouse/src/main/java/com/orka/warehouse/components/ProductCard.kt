package com.orka.warehouse.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.core.Formatter
import com.orka.products.Product
import com.orka.res.Drawables
import com.orka.res.Strings

@Composable
internal fun ProductCard(
    modifier: Modifier = Modifier,
    item: Product,
    formatter: Formatter,
    selectProduct: (Product) -> Unit
) {

    ListItem(
        modifier = modifier.clickable { selectProduct(item) },
        headlineContent = {
            Text(formatter.formatCurrency(item.price, stringResource(Strings.uzs)))
        },
        overlineContent = { Text(item.name) },
        trailingContent = {
            Icon(
                painter = painterResource(Drawables.keyboard_arrow_right),
                contentDescription = ""
            )
        },
        supportingContent = { Text(item.description) },
        leadingContent = { Text(item.id.toString()) }
    )
}