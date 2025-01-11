package com.orka.warehouse.parts

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.orka.components.Dialog
import com.orka.components.ExposedDropDownTextField
import com.orka.components.VerticalSpacer
import com.orka.products.Product
import com.orka.res.Strings
import com.orka.string.containsOnlyZeroes

@Composable
fun ReceiveDialog(
    dismissRequest: () -> Unit,
    products: List<Product>,
    addReceive: (Product, Int, Double, String) -> Unit,
    currencyVisualTransformation: VisualTransformation
) {
    val amount = rememberSaveable { mutableStateOf("") }
    val price = rememberSaveable { mutableStateOf("") }
    val comment = rememberSaveable { mutableStateOf("") }
    val menuExpanded = rememberSaveable { mutableStateOf(false) }
    val selected = rememberSaveable { mutableStateOf<Product?>(null) }
    val noComment = stringResource(Strings.no_comment)

    Dialog(
        dismissRequest = dismissRequest,
        title = stringResource(Strings.receive_new_products),
        supportingText = stringResource(Strings.fill_lines_to_receive_a_product),
        onSuccess = {
                selected.value?.let { product ->
                    if (amount.value.isNotBlank() && price.value.isNotBlank()) {
                        addReceive(
                            product,
                            amount.value.toIntOrNull() ?: 0,
                            price.value.toDoubleOrNull() ?: 0.0,
                            comment.value.ifBlank { noComment }
                        )
                    }
                }
        }
    ) {

        ExposedDropDownTextField(
            menuExpanded = menuExpanded.value,
            onExpandChange = { menuExpanded.value = it },
            onDismissRequested = { menuExpanded.value = false },
            text = selected.value?.name ?: stringResource(Strings.select_the_product),
            items = products,
            itemText = { it.name },
            onItemSelected = { selected.value = it }
        )

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