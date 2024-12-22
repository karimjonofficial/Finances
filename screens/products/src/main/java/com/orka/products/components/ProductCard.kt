package com.orka.products.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.orka.products.Product
import com.orka.res.Drawables

@Composable
internal fun ProductCard(
    modifier: Modifier = Modifier,
    product: Product,
    formatCurrency: (Double) -> String
) {

    ListItem(
        modifier = modifier.clickable {  },
        headlineContent = { Text(formatCurrency(product.price)) },
        overlineContent = { Text(product.name) },
        trailingContent = {
            Icon(
                painter = painterResource(Drawables.keyboard_arrow_right),
                contentDescription = ""
            )
        },
        supportingContent = { Text(product.description) },
        leadingContent = { Text(product.id.toString()) }
    )
}