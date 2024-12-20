package com.orka.home.parts

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.res.Strings
import com.orka.ui.Dialog
import com.orka.ui.VerticalSpacer

@Composable
fun AddCategoryDialog(
    modifier: Modifier = Modifier,
    dismissRequest: () -> Unit,
    addClick: (String, String) -> Unit
) {

    val name = rememberSaveable { mutableStateOf("") }
    val description = rememberSaveable { mutableStateOf("") }

    Dialog(
        modifier = modifier,
        dismissRequest = dismissRequest,
        title = stringResource(Strings.add_a_category),
        supportingText = stringResource(Strings.fill_lines_to_add_a_new_category),
        onSuccess = { addClick(name.value, description.value) }
    ) {

        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text(stringResource(Strings.name)) },
            singleLine = true
        )

        VerticalSpacer(8)

        OutlinedTextField(
            value = description.value,
            onValueChange = { description.value = it },
            label = { Text(stringResource(Strings.description)) }
        )
    }
}