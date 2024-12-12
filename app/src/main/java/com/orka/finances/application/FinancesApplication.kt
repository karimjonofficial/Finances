package com.orka.finances.application

import android.app.Application
import com.orka.di.AppContainer

class FinancesApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}