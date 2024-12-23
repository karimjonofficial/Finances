package com.orka.basket

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.basket.parts.PriceContent
import com.orka.basket.parts.BasketItemsList
import com.orka.core.Formatter
import com.orka.res.Strings

@Composable
internal fun BasketScreenContent(
    modifier: Modifier,
    viewModel: BasketScreenViewModel,
    lazyListState: LazyListState,
    formatter: Formatter
) {

    viewModel.fetch()
    val uiState = viewModel.uiState.collectAsState()

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

                if(uiState.value !is BasketScreenState.Ready.Edit) {
                    Button(
                        modifier = Modifier.size(width = 180.dp, height = 58.dp),
                        enabled = uiState.value is BasketScreenState.Ready,
                        onClick = { viewModel.sale() },
                        elevation = ButtonDefaults.elevatedButtonElevation()
                    ) {
                        Text(text = stringResource(Strings.sell))
                    }
                }
            }
        }
    }
}