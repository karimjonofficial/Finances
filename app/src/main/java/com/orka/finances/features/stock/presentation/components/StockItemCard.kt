package com.orka.finances.features.stock.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.finances.R
import com.orka.finances.features.stock.models.Product
import com.orka.finances.features.stock.models.StockItem
import com.orka.finances.lib.ui.VerticalSpacer

@Composable
fun StockItemCard(
    modifier: Modifier = Modifier,
    stockItem: StockItem,
    click: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(24.dp))
            .clickable { click(stockItem.id) }
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .weight(1f)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {

            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(R.drawable.furniture1),
                contentScale = ContentScale.Crop,
                contentDescription = stockItem.product.name,
            )
        }

        VerticalSpacer(8)

        Row(verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = stockItem.product.name,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(4.dp)
            ) {
                Text(
                    text = "${stockItem.amount} left",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }

        Text(
            text = "Tannarx: " + stockItem.product.price.toString(),
            style = MaterialTheme.typography.titleMedium
        )

        VerticalSpacer(8)
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ProductCardPreview() {
    val sofaUrl = ""
        //"https://ergomebel.uz/image/cachewebp/catalog/photos/1051/divan-ergo-chester-1-680x600.webp"
    val product = Product(
        id = 1,
        name = "Product1",
        price = 1000.0,
        imgSrc = sofaUrl
    )

    val stockItem = StockItem(1, product, 1, 100)

    Surface {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            StockItemCard(
                modifier = Modifier.size(height = 200.dp, width = 180.dp),
                stockItem = stockItem
            ) {}
        }
    }
}