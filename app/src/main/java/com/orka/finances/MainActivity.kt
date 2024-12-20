package com.orka.finances

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.orka.finances.application.FinancesApplication
import com.orka.finances.ui.FinancesScreen
import com.orka.finances.ui.theme.FinancesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = Color.WHITE,
                darkScrim = Color.BLACK
            ),
            navigationBarStyle = SystemBarStyle.light(
                scrim = Color.WHITE,
                darkScrim = Color.BLACK
            )
        )

        super.onCreate(savedInstanceState)

        setContent {

            val app = application as FinancesApplication
            val appContainer = app.container

            FinancesTheme {

                FinancesScreen(
                    container = appContainer,
                    appViewModel = appContainer.mainViewModel
                )
            }
        }
    }
}