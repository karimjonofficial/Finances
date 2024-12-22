package com.orka.basket.parts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.basket.Basket
import com.orka.res.Strings

@Composable
internal fun BasketScreenContent(
    modifier: Modifier,
    basket: Basket,
    lazyListState: LazyListState
) {

    Column(modifier = modifier) {

        BasketItemsList(
            modifier = Modifier.weight(1f),
            items = basket.items,
            state = lazyListState
        )

        Box(modifier = Modifier.imePadding().background(BottomAppBarDefaults.containerColor)) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {

                Column(modifier = Modifier.weight(1f)) {

                    Text(text = stringResource(Strings.overall_price))

                    Text(
                        text = basket.price.toString(),
                        style = MaterialTheme.typography.titleLarge
                    )
                }

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