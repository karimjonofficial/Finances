package com.orka.finances

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import android.graphics.Color
import com.orka.finances.application.FinancesApplication
import com.orka.finances.ui.FinancesScreen
import com.orka.finances.ui.theme.FinancesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT
            )
        )

        super.onCreate(savedInstanceState)

        setContent {

            val app = application as FinancesApplication
            val mainContainer = app.container

            FinancesTheme {
                FinancesScreen(viewModel = mainContainer.mainViewModel)
            }
        }
    }
}