package com.orka.finances

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.orka.finances.application.FinancesApplication
import com.orka.finances.ui.FinancesScreen
import com.orka.finances.ui.theme.FinancesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            val app = application as FinancesApplication
            val appContainer = app.container
            val view = LocalView.current

            if (!view.isInEditMode) {
                val window = (view.context as Activity).window
                val insetsController = WindowCompat.getInsetsController(window, view)

                window.statusBarColor = android.graphics.Color.WHITE
                insetsController.isAppearanceLightStatusBars = true
            }

            WindowInsets

            FinancesTheme {

                FinancesScreen(
                    container = appContainer,
                    appViewModel = appContainer.getAppViewModel()
                )
            }
        }
    }
}