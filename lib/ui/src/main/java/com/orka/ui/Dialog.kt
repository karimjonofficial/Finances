package com.orka.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.res.Strings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dialog(
    modifier: Modifier = Modifier,
    dismissRequest: () -> Unit,
    title: String,
    supportingText: String,
    cancelTitle: String = stringResource(Strings.cancel),
    successTitle: String = stringResource(Strings.add),
    onCancel: (() -> Unit)? = null,
    onSuccess: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {

    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = { dismissRequest() }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors().copy(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {

                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall
                )
                VerticalSpacer(16)

                Text(
                    text = supportingText,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall
                )
                VerticalSpacer(16)

                content()

                VerticalSpacer(24)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = {
                            dismissRequest()
                            onCancel?.invoke()
                        }
                    ) {
                        Text(
                            text = cancelTitle,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    HorizontalSpacer(8)

                    TextButton(
                        onClick = {
                            onSuccess()
                            dismissRequest()
                        }
                    ) {
                        Text(
                            text = successTitle,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}