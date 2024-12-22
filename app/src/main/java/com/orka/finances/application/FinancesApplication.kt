package com.orka.finances.application

import android.app.Application
import com.orka.di.SingletonContainer

class FinancesApplication : Application() {
    lateinit var container: SingletonContainer

    override fun onCreate() {
        super.onCreate()
        container = SingletonContainer(this)
    }
}