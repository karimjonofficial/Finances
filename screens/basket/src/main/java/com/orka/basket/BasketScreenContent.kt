package com.orka.basket

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.basket.parts.BasketItemsList
import com.orka.basket.parts.PriceContent
import com.orka.basket.parts.SellDialog
import com.orka.core.Formatter
import com.orka.core.Printer
import com.orka.res.Strings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
internal fun BasketScreenContent(
    modifier: Modifier,
    viewModel: BasketScreenViewModel,
    lazyListState: LazyListState,
    formatter: Formatter,
    printer: Printer
) {

    val uiState = viewModel.uiState.collectAsState()
    val dialogVisible = rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope { Dispatchers.IO }

    if(dialogVisible.value) {

        if(uiState.value is BasketScreenState.WithBasket.Regular) {
            SellDialog(
                dismissRequest = { dialogVisible.value = false },
                basket = (uiState.value as BasketScreenState.WithBasket.Regular).basket,
                print = {
                    coroutineScope.launch(Dispatchers.Default) {
                        printer.print(it.toImageBitmap().asAndroidBitmap().copy(Bitmap.Config.ARGB_8888, false))
                    }
                },
                sellProducts = { viewModel.sell() },
                formatter = formatter
            )
        }
    }

    Column(modifier = modifier.fillMaxSize()) {

        BasketItemsList(
            modifier = Modifier.weight(1f),
            viewModel = viewModel,
            lazyListState = lazyListState,
            formatter = formatter
        )

        Box(
            modifier = Modifier
                .imePadding()
                .background(BottomAppBarDefaults.containerColor)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {

                Column(modifier = Modifier.weight(1f)) {

                    PriceContent(
                        state = uiState.value,
                        formatter = formatter,
                        edit = { viewModel.edit() },
                        setPrice = { viewModel.setPrice(it) },
                        stopEditing = { viewModel.stopEditing() }
                    )
                }

                if (uiState.value is BasketScreenState.WithBasket.Regular) {
                    Button(
                        modifier = Modifier.size(width = 180.dp, height = 58.dp),
                        enabled = uiState.value is BasketScreenState.WithBasket,
                        onClick = { dialogVisible.value = true },
                        elevation = ButtonDefaults.elevatedButtonElevation()
                    ) {
                        Text(text = stringResource(Strings.sell))
                    }
                }
            }
        }
    }
}