package com.orka.warehouse.parts

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.res.Drawables
import com.orka.res.Strings
import com.orka.ui.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WarehouseScreenTopBar(
    addClick: () -> Unit,
    importClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopBar(
        title = stringResource(Strings.warehouse),
        actions = {

            IconButton(onClick = addClick) {
                Icon(
                    painter = painterResource(Drawables.add),
                    contentDescription = "Add"
                )
            }

            IconButton(onClick = importClick) {
                Icon(
                    painter = painterResource(Drawables.download),
                    contentDescription = stringResource(Strings.receive_new_products)
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}