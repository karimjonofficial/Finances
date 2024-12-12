package com.orka.composables.screens.parts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.composables.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreenTopBar(scanClick: () -> Unit) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(R.drawable.person_image),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = stringResource(R.string.good_morning),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = stringResource(R.string.karimjon_dev),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = { scanClick() }) {
                Icon(
                    painter = painterResource(R.drawable.qr_code_scanner),
                    contentDescription = stringResource(R.string.scan_a_qr_code)
                )
            }
        }
    )
}