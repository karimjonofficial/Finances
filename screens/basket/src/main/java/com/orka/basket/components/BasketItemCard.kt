package com.orka.basket.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.basket.BasketItem
import com.orka.core.Formatter
import com.orka.core.FormatterImpl
import com.orka.products.Product
import com.orka.res.Drawables
import com.orka.res.Strings
import com.orka.ui.HorizontalSpacer
import com.orka.ui.VerticalSpacer

@Composable
internal fun BasketItemCard(
    modifier: Modifier = Modifier,
    item: BasketItem,
    increaseClick: (Int) -> Unit,
    decreaseClick: (Int) -> Unit,
    removeClick: (Int) -> Unit,
    formatter: Formatter
) {
    Card(modifier = modifier) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize().padding(12.dp)
        ) {

            Image(
                modifier = Modifier
                    .size(120.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(MaterialTheme.colorScheme.surface),
                painter = painterResource(Drawables.furniture1),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )

            HorizontalSpacer(32)

            Column(modifier = Modifier.weight(1f)) {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        modifier = Modifier.weight(1f),
                        text = item.product.name,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.titleMedium
                    )

                    HorizontalSpacer(8)

                    IconButton(onClick = { removeClick(item.product.id) }) {
                        Icon(
                            painter = painterResource(Drawables.close),
                            contentDescription = stringResource(Strings.delete)
                        )
                    }
                }

                VerticalSpacer(8)

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        modifier = Modifier.weight(1f),
                        text = formatter.formatCurrency(
                            value = item.product.price,
                            currencyName = stringResource(Strings.uzs)
                        ),
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.titleMedium
                    )

                    HorizontalSpacer(8)

                    Box(
                        contentAlignment = Alignment.CenterEnd,
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.large)
                            .background(MaterialTheme.colorScheme.surface)
                    ) {

                        Row(
                            modifier = Modifier.size(width = 96.dp, height = 36.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            TextButton(
                                modifier = Modifier.size(height = 36.dp, width = 32.dp),
                                onClick = { decreaseClick(item.product.id) }
                            ) {
                                Text(text = "-", fontWeight = FontWeight.Bold)
                            }

                            Text(
                                modifier = Modifier.weight(1f),
                                text = item.amount.toString(),
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )

                            TextButton(
                                modifier = Modifier.size(height = 36.dp, width = 32.dp),
                                onClick = { increaseClick(item.product.id) }
                            ) {
                                Text(text = "+", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun BasketItemCardPreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        val item = BasketItem(
            product = Product(
                id = 1,
                name = "Product",
                price = 10000.0,
                description = "",
                categoryId = 1
            ),
            amount = 1000
        )

        BasketItemCard(
            modifier = Modifier.padding(16.dp).height(160.dp), item = item,
            increaseClick = {},
            decreaseClick = {},
            removeClick = {},
            formatter = FormatterImpl()
        )
    }
}