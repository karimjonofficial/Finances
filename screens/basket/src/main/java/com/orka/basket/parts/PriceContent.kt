package com.orka.basket.parts

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.orka.basket.BasketScreenState
import com.orka.core.Formatter
import com.orka.res.Drawables
import com.orka.res.Strings
import com.orka.ui.HorizontalSpacer

@Composable
internal fun PriceContent(
    state: BasketScreenState,
    formatter: Formatter,
    edit: () -> Unit,
    setPrice: (Double) -> Unit,
    stopEditing: () -> Unit
) {
    when (state) {

        is BasketScreenState.Ready.Regular -> {

            RegularPriceContent(
                formatter = formatter,
                price = state.basket.price,
                edit = { edit() }
            )
        }

        is BasketScreenState.Ready.Edit -> {

            EditPriceContent(
                price = state.basket.price,
                setPrice = { setPrice(it) },
                stopEditing = { stopEditing() }
            )
        }

        BasketScreenState.Initial -> {}
        BasketScreenState.Selling -> {
            Text(stringResource(Strings.selling_items))
        }
    }
}

@Composable
private fun RegularPriceContent(
    formatter: Formatter,
    price: Double,
    edit: () -> Unit
) {

    Text(text = stringResource(Strings.overall_price))

    Row(verticalAlignment = Alignment.CenterVertically) {

        Text(
            text = formatter.formatCurrency(price, stringResource(Strings.uzs)),
            style = MaterialTheme.typography.titleLarge
        )

        HorizontalSpacer(8)

        IconButton(onClick = { if (price > 0.0) { edit() } }) {

            Icon(
                painter = painterResource(Drawables.edit_outlined),
                contentDescription = stringResource(Strings.edit)
            )
        }
    }
}

@Composable
private fun EditPriceContent(
    price: Double,
    setPrice: (Double) -> Unit,
    stopEditing: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {

        val text = rememberSaveable {
            mutableStateOf(price.toString())
        }

        OutlinedTextField(
            value = text.value,
            onValueChange = { text.value = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            label = { Text(text = stringResource(Strings.overall_price)) },
            suffix = { Text(text = stringResource(Strings.uzs)) }
        )

        HorizontalSpacer(8)

        IconButton(
            onClick = {
                if (text.value.isNotEmpty() && text.value.toDoubleOrNull() != null) {
                    setPrice(text.value.toDouble())
                }
            }
        ) {

            Icon(
                painter = painterResource(Drawables.check),
                contentDescription = stringResource(Strings.done)
            )
        }

        HorizontalSpacer(8)

        IconButton(onClick = { stopEditing() }) {

            Icon(
                painter = painterResource(Drawables.close),
                contentDescription = stringResource(Strings.close)
            )
        }
    }
}