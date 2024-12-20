package com.orka.stock.parts

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.res.Drawables
import com.orka.res.Strings
import com.orka.ui.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun StockScreenTopBar(onImportClick: () -> Unit) {
    TopBar(
        title = stringResource(Strings.products),
        actions = {
            IconButton(onClick = onImportClick) {
                Icon(
                    painter = painterResource(Drawables.download),
                    contentDescription = stringResource(Strings.receive_new_products)
                )
            }
        }
    )
}