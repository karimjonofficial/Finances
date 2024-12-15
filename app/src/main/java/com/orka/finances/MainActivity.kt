package com.orka.finances

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import com.orka.finances.application.FinancesApplication
import com.orka.finances.ui.FinancesScreen
import com.orka.finances.ui.theme.FinancesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            /**val view = LocalView.current

            if (!view.isInEditMode) {
                val window = (view.context as Activity).window
                val insetsController = WindowCompat.getInsetsController(window, view)

                window.statusBarColor = android.graphics.Color.WHITE
                insetsController.isAppearanceLightStatusBars = true
            }

            WindowCompat.getInsetsController(window, window.decorView)
                .isAppearanceLightStatusBars = false**/

            val app = application as FinancesApplication
            val appContainer = app.container

            FinancesTheme {

                FinancesScreen(
                    modifier = Modifier.safeDrawingPadding(),
                    container = appContainer,
                    appViewModel = appContainer.getAppViewModel()
                )
            }
        }
    }
}