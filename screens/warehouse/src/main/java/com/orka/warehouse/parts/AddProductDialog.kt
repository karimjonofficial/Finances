package com.orka.warehouse.parts

import androidx.compose.foundation.text.KeyboardOptions
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
import com.orka.components.NumberTextField
import com.orka.components.VerticalSpacer
import com.orka.core.Formatter
import com.orka.res.Strings
import com.orka.string.containsOnlyZeroes

@Composable
fun AddProductDialog(
    modifier: Modifier = Modifier,
    dismissRequest: () -> Unit,
    onSuccess: (String, Double, String, Int) -> Unit,
    currencyVisualTransformation: VisualTransformation,
    formatter: Formatter
) {
    val name = rememberSaveable { mutableStateOf("") }
    val price = rememberSaveable { mutableStateOf("") }
    val description = rememberSaveable { mutableStateOf("") }
    val amount = rememberSaveable { mutableIntStateOf(0) }
    val noComment = stringResource(Strings.no_comment)

    Dialog(
        modifier = modifier,
        dismissRequest = dismissRequest,
        title = stringResource(Strings.add_a_product),
        supportingText = stringResource(Strings.fill_lines_to_add_a_new_product),
        onSuccess = {
            if(price.value.isNotBlank()) {
                onSuccess(
                    name.value,
                    price.value.toDouble(),
                    description.value.ifBlank { noComment },
                    amount.intValue
                )
            }
        }
    ) {
        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text(stringResource(Strings.name)) },
            singleLine = true
        )

        VerticalSpacer(8)

        OutlinedTextField(
            value = price.value,
            onValueChange = {  if(!it.containsOnlyZeroes()) price.value = it  },
            label = { Text(stringResource(Strings.price)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            suffix = { Text(stringResource(Strings.uzs)) },
            visualTransformation = currencyVisualTransformation
        )

        VerticalSpacer(8)

        OutlinedTextField(
            value = description.value,
            onValueChange = { description.value = it },
            label = { Text(stringResource(Strings.description)) }
        )

        VerticalSpacer(8)

        NumberTextField(
            value = amount.intValue,
            onValueChange = { amount.intValue = it },
            formatter = formatter
        )
    }
}