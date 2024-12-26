package com.orka.warehouse.components

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.core.Formatter
import com.orka.core.FormatterImpl
import com.orka.products.Product
import com.orka.res.Drawables
import com.orka.res.Strings
import com.orka.stock.StockItem
import com.orka.components.VerticalSpacer

@Composable
internal fun StockItemCard(
    modifier: Modifier = Modifier,
    item: StockItem,
    click: (Int) -> Unit,
    addClick: (Product) -> Unit,
    formatter: Formatter
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(24.dp))
            .clickable { click(item.id) }
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
                painter = painterResource(Drawables.furniture1),
                contentScale = ContentScale.Crop,
                contentDescription = item.product.name,
            )

            IconButton(
                modifier = Modifier.align(Alignment.TopEnd),
                onClick = { addClick(item.product) }
            ) {

                Icon(
                    painter = painterResource(Drawables.add),
                    contentDescription = stringResource(Strings.add)
                )
            }
        }

        VerticalSpacer(8)

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Text(
                    text = item.product.name,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(2.dp)
                ) {
                    Text(
                        text = "${item.amount} left",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }

            Text(
                text = "${stringResource(Strings.price)}: ${formatter.formatCurrency(item.product.price, stringResource(Strings.uzs))}",
                style = MaterialTheme.typography.titleMedium
            )
        }

        VerticalSpacer(12)
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ProductCardPreview() {
    val product = Product(
        id = 1,
        name = "Product1",
        price = 1000.0,
        description = "",
        categoryId = 0
    )

    val stockItem = StockItem(1, product, 100)

    Surface {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            StockItemCard(
                modifier = Modifier.size(height = 200.dp, width = 180.dp),
                item = stockItem,
                click = {},
                addClick = {},
                formatter = FormatterImpl()
            )
        }
    }
}