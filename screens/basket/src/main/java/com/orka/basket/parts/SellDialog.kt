package com.orka.basket.parts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.basket.Basket
import com.orka.basket.BasketItem
import com.orka.basket.components.Check
import com.orka.components.Dialog
import com.orka.components.VerticalSpacer
import com.orka.core.Formatter
import com.orka.products.Product
import com.orka.res.Strings
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

@Composable
internal fun SellDialog(
    modifier: Modifier = Modifier,
    dismissRequest: () -> Unit,
    basket: Basket,
    formatter: Formatter,
    print: (GraphicsLayer) -> Unit,
    sellProducts: () -> Unit
) {
    val graphicsLayer = rememberGraphicsLayer()

    Dialog(
        modifier = modifier,
        dismissRequest = dismissRequest,
        title = stringResource(Strings.sell),
        supportingText = stringResource(Strings.do_you_want_print_the_check),
        cancelTitle = stringResource(Strings.no),
        successTitle = stringResource(Strings.yes),
        onSuccess = {
            print(graphicsLayer)
            sellProducts()
        },
        onCancel = { sellProducts() }
    ) {

        VerticalSpacer(
            height = 2,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.inversePrimary)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Check(Modifier, graphicsLayer, basket, formatter)
        }

        VerticalSpacer(
            height = 2,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.inversePrimary)
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun DialogPreview() {
    val range = 1..10
    val products = range.map {
        Product(it, "Product $it", 10000.0, "", 1)
    }

    val items = products.map {
        BasketItem(it, 10)
    }

    val price = 100000.0
    val comment = stringResource(Strings.lorem)
    val basket = Basket(items, price, comment)

    Box(
        modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        SellDialog(
            modifier = Modifier,
            dismissRequest = {},
            basket = basket,
            formatter = object : Formatter {
                override fun formatTime(time: LocalTime): String {
                    return "12.02.2000"
                }

                override fun formatDate(date: LocalDate): String {
                    return "12.02.2000"
                }

                override fun formatCurrency(value: Double, currencyName: String): String {
                    return "2 000 000"
                }

                override fun formatCurrency(value: Double): String {
                    return "2 000 000"
                }

            },
            print = {},
            sellProducts = {}
        )
    }
}