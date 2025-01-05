package com.orka.finances.application

import android.app.Application
import com.orka.containers.MainContainer

class FinancesApplication : Application() {

    lateinit var container: MainContainer

    override fun onCreate() {
        super.onCreate()
        container = MainContainer(this)
    }
}