package com.orka.products.components

import android.icu.text.DecimalFormat
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
import com.orka.products.Product

@Composable
internal fun ProductCard(
    modifier: Modifier = Modifier,
    product: Product,
    format: DecimalFormat
) {
    ListItem(
        modifier = modifier.clickable {  },
        headlineContent = { Text("${format.format(product.price).replace(",", " ")} so'm") }, //TODO
        overlineContent = { Text(product.name) },
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
        ProductCard(product = product, format = DecimalFormat("#,###"))
    }
}