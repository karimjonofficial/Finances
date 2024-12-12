package com.orka.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.orka.base.CategoriesDataSource
import com.orka.base.LoginDataSource
import com.orka.base.StockDataSource
import com.orka.data.UserInfoDataSource
import com.orka.database.FinancesDb
import com.orka.lib.models.Credential
import com.orka.network.getCategoriesDataSource
import com.orka.network.getLoginDataSource
import com.orka.network.getStockDataSource
import com.orka.viewmodels.AppViewModel
import com.orka.viewmodels.HomeScreenViewModel
import com.orka.viewmodels.LoginScreenViewModel
import com.orka.viewmodels.StockScreenViewModel
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class AppContainer(private val context: Context) {

    private var appViewModel: AppViewModel? = null
    private var homeViewModel: HomeScreenViewModel? = null
    private var stockViewModel: StockScreenViewModel? = null

    private val baseUrl = "http://45.90.216.251:7000/api/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val userInfoDataSource: UserInfoDataSource by lazy {
        UserInfoDataSourceAdapter(FinancesDb.getDatabase(context).userInfoDao())
    }
    private val loginDataSource: LoginDataSource by lazy { getLoginDataSource(retrofit) }

    private fun getCategoriesDataSource(credential: Credential): CategoriesDataSource {
        return getCategoriesDataSource(retrofit, credential)
    }

    private fun getStockDataSource(credential: Credential): StockDataSource {
        return getStockDataSource(retrofit, credential)
    }

    fun getAppViewModel(): AppViewModel {
        return appViewModel ?: AppViewModel(userInfoDataSource).apply { appViewModel = this }
    }

    fun getLoginScreenViewModel(): LoginScreenViewModel {
        return LoginScreenViewModel(loginDataSource) {
            getAppViewModel().setCredentials(it)
        }
    }

    fun getHomeScreenViewModel(credential: Credential, navigate: (Int) -> Unit): HomeScreenViewModel {
        return homeViewModel ?: HomeScreenViewModel(
            dataSource = getCategoriesDataSource(credential),
            navigate = { navigate(it) },
            unauthorize = { getAppViewModel().unauthorize() }
        ). apply { homeViewModel = this }
    }

    fun getStockScreenViewModel(credential: Credential, categoryId: Int, navigate: (Int) -> Unit): StockScreenViewModel {
        return stockViewModel ?: StockScreenViewModel(
            categoryId = categoryId,
            dataSource = getStockDataSource(credential),
            navigate = { navigate(it) },
            unauthorize = { getAppViewModel().unauthorize() }
        ).apply { stockViewModel = this }
    }
}