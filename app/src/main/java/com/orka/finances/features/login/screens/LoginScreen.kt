package com.orka.finances.features.login.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.finances.R
import com.orka.finances.lib.components.Spacer16
import com.orka.finances.lib.components.Spacer8

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    enterClick: () -> Unit
) {
    Column(
        modifier = modifier.padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val usernameText = rememberSaveable { mutableStateOf("") }
        val passwordText = rememberSaveable { mutableStateOf("") }
        val shouldRemember = rememberSaveable { mutableStateOf(false) }
        val isPasswordVisible = rememberSaveable { mutableStateOf(false) }

        Spacer16(times = 2)

        Icon(
            modifier = Modifier.size(100.dp),
            painter = painterResource(R.drawable.logo),
            contentDescription = null
        )

        Spacer16()

        Text(
            text = stringResource(R.string.enter_to_account),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer16()

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().height(60.dp),
            singleLine = true,
            value = usernameText.value,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null
                )
            },
            onValueChange = { usernameText.value = it },
            label = {
                Text(stringResource(R.string.username))
            }
        )

        Spacer8()

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().height(60.dp),
            singleLine = true,
            value = passwordText.value,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = null
                )
            },
            visualTransformation = if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(
                    onClick = { isPasswordVisible.value = !isPasswordVisible.value }
                ) {
                    Icon(
                        painter = painterResource(if (isPasswordVisible.value) R.drawable.visibility else R.drawable.visibility_off),
                        contentDescription = null
                    )
                }
            },
            onValueChange = { passwordText.value = it },
            label = {
                Text(stringResource(R.string.password))
            }
        )

        Spacer8()

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { shouldRemember.value = !shouldRemember.value }
        ) {
            Checkbox(checked = shouldRemember.value, onCheckedChange = { shouldRemember.value = it })
            Spacer(Modifier.width(8.dp))
            Text(stringResource(R.string.remember_me))
        }

        Spacer8()

        Button(
            modifier = Modifier.fillMaxWidth().height(58.dp),
            onClick = { enterClick() }
        ) {
            Text(stringResource(R.string.enter))
        }

        Spacer8()

        Text(stringResource(R.string.forgot_password))
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun LoginScreenPreview() {
    Scaffold { innerPadding ->
        LoginScreen(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            enterClick = {}
        )
    }
}