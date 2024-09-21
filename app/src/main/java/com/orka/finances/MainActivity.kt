package com.orka.finances

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.orka.finances.features.home.screens.HomeScreen
import com.orka.finances.features.home.viewmodels.HomeScreenViewModel
import com.orka.finances.ui.theme.FinancesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinancesTheme {
                val homeScreenViewModel = HomeScreenViewModel()
                HomeScreen(homeScreenViewModel)
            }
        }
    }
}