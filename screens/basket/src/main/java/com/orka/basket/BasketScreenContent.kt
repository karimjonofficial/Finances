package com.orka.basket

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.orka.basket.parts.BasketItemsList
import com.orka.core.Formatter
import com.orka.res.Drawables
import com.orka.res.Strings
import com.orka.ui.HorizontalSpacer

@Composable
internal fun BasketScreenContent(
    modifier: Modifier,
    viewModel: BasketScreenViewModel,
    lazyListState: LazyListState,
    formatter: Formatter
) {

    viewModel.fetch()
    val uiState = viewModel.uiState.collectAsState()

    Column(modifier = modifier) {

        BasketItemsList(
            modifier = Modifier.weight(1f),
            viewModel = viewModel,
            state = lazyListState,
            formatter = formatter
        )

        Box(
            modifier = Modifier
                .imePadding()
                .background(BottomAppBarDefaults.containerColor)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {

                Column(modifier = Modifier.weight(1f)) {

                    when(uiState.value) {

                        is BasketScreenState.Regular -> {

                            Text(text = stringResource(Strings.overall_price))

                            Row(verticalAlignment = Alignment.CenterVertically) {

                                Text(
                                    text = formatter.formatCurrency(
                                        uiState.value.basket.price, stringResource(Strings.uzs)
                                    ),
                                    style = MaterialTheme.typography.titleLarge
                                )

                                HorizontalSpacer(8)

                                IconButton(
                                    onClick = {
                                        if(uiState.value.basket.price > 0.0) {
                                            viewModel.edit()
                                        }
                                    }
                                ) {

                                    Icon(
                                        painter = painterResource(Drawables.edit_outlined),
                                        contentDescription = stringResource(Strings.edit)
                                    )
                                }
                            }
                        }
                        is BasketScreenState.Edit -> {

                            Row(verticalAlignment = Alignment.CenterVertically) {

                                val text = rememberSaveable {
                                    mutableStateOf(uiState.value.basket.price.toString())
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
                                        if(text.value.isNotEmpty() && text.value.isDigitsOnly()) {
                                            viewModel.setPrice(text.value.toDouble())
                                        }
                                    }
                                ) {

                                    Icon(
                                        painter = painterResource(Drawables.check),
                                        contentDescription = stringResource(Strings.done)
                                    )
                                }
                            }
                        }
                    }
                }

                if(uiState.value is BasketScreenState.Regular) {
                    Button(
                        modifier = Modifier.size(width = 180.dp, height = 58.dp),
                        onClick = {},
                        elevation = ButtonDefaults.elevatedButtonElevation()
                    ) {
                        Text(text = stringResource(Strings.sell))
                    }
                }
            }
        }
    }
}