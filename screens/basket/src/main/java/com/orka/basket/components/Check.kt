package com.orka.basket.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.basket.Basket
import com.orka.basket.BasketItem
import com.orka.components.HorizontalSpacer
import com.orka.components.VerticalSpacer
import com.orka.core.Formatter
import com.orka.products.Product
import com.orka.res.Drawables
import com.orka.res.Strings
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import java.util.Calendar
import java.time.LocalDateTime as jtLocalDateTime

@Composable
internal fun Check(
    modifier: Modifier = Modifier,
    graphicsLayer: GraphicsLayer,
    basket: Basket,
    formatter: Formatter
) {
    Column(
        modifier = modifier
            .drawWithContent {
                graphicsLayer.record {
                    this@drawWithContent.drawContent()
                }
                drawLayer(graphicsLayer)
            }
            .padding(horizontal = 8.dp)
            .wrapContentHeight()
            .verticalScroll(rememberScrollState()),
    ) {
        Header(formatter = formatter)
        ItemsList(formatter = formatter, items = basket.items)
        Footer(formatter = formatter, basket = basket)
    }
}

@Composable
private fun Footer(formatter: Formatter, basket: Basket) {
    Text(
        text = "${stringResource(Strings.price)}: ${formatter.formatCurrency(basket.price, stringResource(Strings.uzs))}",
        style = MaterialTheme.typography.labelSmall
    )

    Text(
        text = "${stringResource(Strings.comment)}: ${basket.comment}",
        style = MaterialTheme.typography.labelSmall
    )
}

@Composable
private fun Header(formatter: Formatter) {
    Row(modifier = Modifier.fillMaxWidth()) {

        Image(painterResource(Drawables.logo), null)
        HorizontalSpacer(16)

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            val now = now()
            Text(stringResource(Strings.finances), fontWeight = FontWeight.Bold)
            Text("${stringResource(Strings.date)}: ${formatter.formatDate(now.date)}")
            Text("${stringResource(Strings.time)}: ${formatter.formatTime(now.time)}")
        }
    }
}

@Composable
fun ItemsList(
    modifier: Modifier = Modifier.fillMaxWidth(),
    formatter: Formatter,
    items: List<BasketItem>
) {
    Row(modifier = modifier) {

        Column {
            Text(
                text = stringResource(Strings.n),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold
            )

            items.forEachIndexed { index, _ ->

                Text(
                    text = (index + 1).toString(),
                    softWrap = false,
                    style = MaterialTheme.typography.labelSmall
                )
                VerticalSpacer(1)
            }
        }

        HorizontalSpacer(4)

        Column(modifier = Modifier.weight(1f)) {

            Text(
                text = stringResource(Strings.name),
                softWrap = false,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold
            )

            items.forEachIndexed { _, item ->
                Text(
                    text = item.product.name,
                    softWrap = false,
                    style = MaterialTheme.typography.labelSmall
                )
                VerticalSpacer(1)
            }
        }

        HorizontalSpacer(4)

        Column {

            Text(
                text = "${stringResource(Strings.price)}(uzs)",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold
            )

            items.forEachIndexed { _, item ->
                Text(
                    text = formatter.formatCurrency(item.product.price),
                    style = MaterialTheme.typography.labelSmall
                )
                VerticalSpacer(1)
            }
        }

        HorizontalSpacer(4)

        Column(horizontalAlignment = Alignment.End) {

            Text(
                text = stringResource(Strings.amount),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold
            )

            items.forEachIndexed { _, item ->
                Text(
                    text = item.amount.toString(),
                    style = MaterialTheme.typography.labelSmall
                )
                VerticalSpacer(1)
            }
        }
    }
}

private fun now(): LocalDateTime {
    return if (isNewVersion()) getDateTimeForNewVersions()
    else getDateTimeForOldVersions()
}

private fun isNewVersion() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

private fun getDateTimeForOldVersions(): LocalDateTime {
    val calendar = Calendar.getInstance()

    return LocalDateTime(
        year = calendar.get(Calendar.YEAR),
        monthNumber = calendar.get(Calendar.MONTH),
        dayOfMonth = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH),
        hour = calendar.get(Calendar.HOUR),
        minute = calendar.get(Calendar.MINUTE),
        second = calendar.get(Calendar.SECOND)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
private fun getDateTimeForNewVersions(): LocalDateTime {
    val now = jtLocalDateTime.now()

    return LocalDateTime(
        year = now.year,
        month = now.month,
        dayOfMonth = now.dayOfMonth,
        hour = now.hour,
        minute = now.minute,
        second = now.second,
        nanosecond = now.nano
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun CheckPreview() {

    val graphicsLayer = rememberGraphicsLayer()
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
            .padding(16.dp)
    ) {
        Check(Modifier, graphicsLayer, basket, object : Formatter {
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

        })
    }
}