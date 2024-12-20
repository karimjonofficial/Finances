package com.orka.products.parts

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.orka.res.Strings
import com.orka.ui.Dialog
import com.orka.ui.VerticalSpacer

@Composable
fun AddProductDialog(
    modifier: Modifier = Modifier,
    dismissRequest: () -> Unit,
    addClick: (String, Double, String) -> Unit
) {
    val name = rememberSaveable { mutableStateOf("") }
    val price = rememberSaveable { mutableStateOf("") }
    val description = rememberSaveable { mutableStateOf("") }

    Dialog(
        modifier = modifier,
        dismissRequest = dismissRequest,
        title = stringResource(Strings.add_a_product),
        supportingText = stringResource(Strings.fill_lines_to_add_a_new_product),
        onSuccess = {
            if(price.value.isNotBlank())
                addClick(name.value, price.value.toDouble(), description.value)
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
            onValueChange = { price.value = it },
            label = { Text(stringResource(Strings.price)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            suffix = { Text(stringResource(Strings.uzs)) }
        )

        VerticalSpacer(8)

        OutlinedTextField(
            value = description.value,
            onValueChange = { description.value = it },
            label = { Text(stringResource(Strings.description)) }
        )
    }
}