package com.orka.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.orka.base.Product

@Composable
internal fun ProductCard(
    modifier: Modifier = Modifier,
    product: Product
) {
    ListItem(
        modifier = modifier.clickable {  },
        headlineContent = { Text(product.name) },
        overlineContent = { Text(product.price.toString()) },
        trailingContent = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = ""
            )
        },
        supportingContent = { Text(product.description) },
        leadingContent = { Text(product.id.toString()) }
    )
}

@Preview
@Composable
private fun ProductCardPreview() {
    val product = Product(1, "Product 1", 1000.0, "Some description", 0)

    Surface(modifier = Modifier.fillMaxSize()) {
        ProductCard(product = product)
    }
}