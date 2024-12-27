package com.orka.main

import android.content.Context
import com.orka.core.UserInfoDataSource
import com.orka.database.FinancesDb
import com.orka.di.SingletonContainer

class MainContainer(private val context: Context) {

    private val userInfoDataSource: UserInfoDataSource by lazy {
        UserInfoDataSourceAdapter(FinancesDb.getDatabase(context).userInfoDao())
    }

    val mainViewModel by lazy { MainViewModel(userInfoDataSource) }
}