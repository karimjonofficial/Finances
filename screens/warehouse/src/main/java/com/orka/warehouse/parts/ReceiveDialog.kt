package com.orka.warehouse.parts

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.orka.components.Dialog
import com.orka.components.VerticalSpacer
import com.orka.products.Product
import com.orka.res.Strings
import com.orka.stock.DialogState
import com.orka.string.containsOnlyZeroes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiveDialog(
    dismissRequest: () -> Unit,
    addReceive: (Product, Int, Double, String) -> Unit,
    state: DialogState,
    currencyVisualTransformation: VisualTransformation
) {
    val productId = rememberSaveable { mutableIntStateOf(0) }
    val amount = rememberSaveable { mutableStateOf("") }
    val price = rememberSaveable { mutableStateOf("") }
    val comment = rememberSaveable { mutableStateOf("") }
    val menuExpanded = rememberSaveable { mutableStateOf(false) }

    Dialog(
        dismissRequest = dismissRequest,
        title = stringResource(Strings.receive_new_products),
        supportingText = stringResource(Strings.fill_lines_to_receive_a_product),
        onSuccess = {
            if (state is DialogState.Initialized) {
                val product = state.products.find { it.id == productId.intValue }

                if (product != null && amount.value.isNotBlank() && price.value.isNotBlank()) {
                    addReceive(
                        product,
                        amount.value.toIntOrNull() ?: 0,
                        price.value.toDoubleOrNull() ?: 0.0,
                        comment.value
                    )
                }
            }
        }
    ) {

        ExposedDropdownMenuBox(
            expanded = menuExpanded.value,
            onExpandedChange = { menuExpanded.value = it }
        ) {
            OutlinedTextField(
                value = if(state is DialogState.Initialized) {
                    state.products.find { it.id == productId.intValue }?.name ?: stringResource(Strings.select_the_product)
                } else stringResource(Strings.select_the_product),
                onValueChange = {},
                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
                readOnly = true,
                label = { Text(stringResource(Strings.product)) },
                singleLine = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuExpanded.value) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )

            ExposedDropdownMenu(
                expanded = menuExpanded.value,
                onDismissRequest = { menuExpanded.value = false }
            ) {
                if(state is DialogState.Initialized) {
                    state.products.forEach {
                        DropdownMenuItem(
                            text = { Text(it.name) },
                            onClick = {
                                productId.intValue = it.id
                                menuExpanded.value = false
                            }
                        )
                    }
                }
            }
        }

        VerticalSpacer(8)

        OutlinedTextField(
            value = amount.value,
            onValueChange = { if (!it.containsOnlyZeroes()) amount.value = it },
            label = { Text(stringResource(Strings.amount)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = currencyVisualTransformation
        )

        VerticalSpacer(8)

        OutlinedTextField(
            value = price.value,
            onValueChange = { if (!it.containsOnlyZeroes()) price.value = it },
            label = { Text(stringResource(Strings.price)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            suffix = { Text(stringResource(Strings.uzs)) },
            visualTransformation = currencyVisualTransformation
        )

        VerticalSpacer(8)

        OutlinedTextField(
            value = comment.value,
            onValueChange = { comment.value = it },
            label = { Text(stringResource(Strings.comment)) }
        )
    }
}