package com.orka.finances

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import com.orka.finances.ui.screens.FinancesAppScreen
import com.orka.finances.ui.theme.FinancesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(Color.White.value.toInt(), Color.White.value.toInt())
        )
        setContent {
            FinancesTheme {
                FinancesAppScreen()
            }
        }
    }
}