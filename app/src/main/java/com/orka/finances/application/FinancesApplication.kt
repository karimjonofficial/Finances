package com.orka.finances.application

import android.app.Application
import com.orka.di.SingletonContainer
import com.orka.main.MainContainer

class FinancesApplication : Application() {

    lateinit var container: MainContainer

    override fun onCreate() {
        super.onCreate()
        container = MainContainer(this)
    }

    suspend fun mainContainer(): MainContainer {
        return MainContainer(this)
    }
}