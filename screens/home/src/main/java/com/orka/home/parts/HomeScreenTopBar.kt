package com.orka.home.parts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.res.Drawables
import com.orka.res.Strings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreenTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    scan: () -> Unit,
) {
    MediumTopAppBar(
        collapsedHeight = 50.dp,
        title = { Text("Finances") },
        navigationIcon = {
            Image(
                painter = painterResource(Drawables.logo),
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.size(width = 48.dp, height = 24.dp),
                contentDescription = "logo"
            )
        },
        actions = {
            IconButton(onClick = { scan() }) {
                Icon(
                    painter = painterResource(Drawables.qr_code_scanner),
                    contentDescription = stringResource(Strings.scan_a_qr_code)
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}