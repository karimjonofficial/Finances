package com.orka.history.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.core.Formatter
import com.orka.receive.Receive
import com.orka.res.Drawables
import com.orka.res.Strings

@Composable
internal fun ReceiveCard(item: Receive, formatter: Formatter) {

    val expanded = rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier.animateContentSize(),
        verticalAlignment = if (expanded.value) Alignment.Bottom else Alignment.CenterVertically
    ) {
        ListItem(
            modifier = Modifier.weight(1f),
            overlineContent = { Text(item.id.toString()) },
            headlineContent = {
                Text(formatter.formatCurrency(item.price, stringResource(Strings.uzs)))
            },
            supportingContent = {

                Column(verticalArrangement = Arrangement.Top) {
                    Text(text = item.comment)
                    if (expanded.value) {
                        Box(modifier = Modifier.height(200.dp)) {}
                    }
                }
            }
        )

        IconButton(onClick = { expanded.value = !expanded.value }) {

            Icon(
                painter = painterResource(
                    if (expanded.value) Drawables.arrow_up else Drawables.arrow_down
                ),
                contentDescription = null
            )
        }
    }
}