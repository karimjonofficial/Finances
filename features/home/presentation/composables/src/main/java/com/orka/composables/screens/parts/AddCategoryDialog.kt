package com.orka.composables.screens.parts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.composables.R
import com.orka.lib.ui.VerticalSpacer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategoryDialog(
    modifier: Modifier = Modifier,
    dismissRequest: () -> Unit,
    addClick: (String, String) -> Unit
) {

    val name = rememberSaveable { mutableStateOf("") }
    val description = rememberSaveable { mutableStateOf("") }

    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = { dismissRequest() }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)) {

                Text(
                    text = stringResource(R.string.add_a_category),
                    style = MaterialTheme.typography.headlineSmall
                )
                VerticalSpacer(16)

                Text(
                    text = stringResource(R.string.fill_lines_to_add_a_new_category),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall
                )
                VerticalSpacer(16)

                OutlinedTextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = { Text(stringResource(R.string.name)) },
                    singleLine = true
                )
                VerticalSpacer(8)

                OutlinedTextField(
                    value = description.value,
                    onValueChange = { description.value = it },
                    label = { Text(stringResource(R.string.description)) }
                )
                VerticalSpacer(24)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = { addClick(name.value, description.value) }
                    ) {
                        Text(
                            text = stringResource(R.string.add),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}