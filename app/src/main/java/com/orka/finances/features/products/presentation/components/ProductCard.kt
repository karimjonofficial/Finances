package com.orka.finances.features.products.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.orka.finances.features.products.models.Product
import com.orka.finances.lib.HorizontalSpacer
import com.orka.finances.lib.VerticalSpacer

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: Product
) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Box(
            modifier = Modifier
                .size(140.dp)
                .clip(RoundedCornerShape(24.dp))
        ) {

            AsyncImage(
                model = product.imgSrc,
                contentScale = ContentScale.FillWidth,
                contentDescription = product.name,
            )
        }

        VerticalSpacer(8)

        Text(
            text = product.name,
            style = MaterialTheme.typography.bodyMedium
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = product.price.toString(),
                style = MaterialTheme.typography.bodyLarge
            )

            HorizontalSpacer(8)

            Box(modifier = Modifier.clip(RoundedCornerShape(4.dp))) {
                Text(
                    text = "${product.amount} left",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProductCardPreview() {
    val sofaUrl = "https://ergomebel.uz/image/cachewebp/catalog/photos/1051/divan-ergo-chester-1-680x600.webp"
    val product = Product(
        id = 1,
        name = "Product1",
        price = 1000.0,
        amount = 100,
        imgSrc = sofaUrl
    )

    Surface {
        Box {
            ProductCard(
                modifier = Modifier.size(200.dp).align(Alignment.Center),
                product = product
            )
        }
    }
}