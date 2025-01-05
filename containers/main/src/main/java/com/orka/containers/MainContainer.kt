package com.orka.containers

import android.content.Context
import com.orka.core.Printer
import com.orka.core.UserInfoDataSource
import com.orka.database.FinancesDb
import com.orka.main.MainViewModel

class MainContainer(private val context: Context) {

    private val db by lazy {
        FinancesDb.getDatabase(context)
    }

    fun getPrinter(context: Context): Printer {
        return PrinterAdapter(context)
    }

    private val userInfoDataSource: UserInfoDataSource by lazy {
        UserInfoDataSourceAdapter(db.userInfoDao())
    }

    val mainViewModel by lazy { MainViewModel(userInfoDataSource) }
}