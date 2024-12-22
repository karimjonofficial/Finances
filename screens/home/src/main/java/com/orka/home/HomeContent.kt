package com.orka.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.home.parts.CategoriesList
import com.orka.res.Drawables
import com.orka.res.Strings
import com.orka.ui.VerticalSpacer

@Composable
internal fun HomeContent(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel,
    lazyGridState: LazyGridState
) {

    val focusManager = LocalFocusManager.current
    val uiState = viewModel.uiState.collectAsState()
    val searchText = rememberSaveable { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    DisposableEffect(Unit) {
        focusManager.clearFocus()
        onDispose { }
    }

    viewModel.fetch()

    Column(modifier = modifier) {

        VerticalSpacer(16)

        OutlinedTextField(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .focusRequester(focusRequester),
            value = searchText.value,
            singleLine = true,
            onValueChange = { searchText.value = it },
            placeholder = { Text(stringResource(Strings.search)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(Drawables.search),
                    contentDescription = stringResource(Strings.search),
                    tint = Color(0xFFBDBDBD)
                )
            },
            trailingIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        painterResource(Drawables.tune),
                        contentDescription = "Tune"
                    )
                }
            },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF5F5F5),
                unfocusedContainerColor = Color(0xFFF5F5F5),
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Transparent,
            )
        )

        VerticalSpacer(16)

        CategoriesList(
            state = lazyGridState,
            categories = uiState.value,
            categoryClick = { viewModel.select(it) }
        )
    }
}