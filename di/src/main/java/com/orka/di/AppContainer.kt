package com.orka.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.orka.core.CategoriesDataSource
import com.orka.core.CredentialsDataSource
import com.orka.core.HttpService
import com.orka.core.HttpServiceImpl
import com.orka.core.ProductsDataSource
import com.orka.core.ReceiveDataSource
import com.orka.core.StockDataSource
import com.orka.core.UserInfoDataSource
import com.orka.credentials.Credential
import com.orka.database.FinancesDb
import com.orka.history.HistoryScreenViewModel
import com.orka.home.HomeScreenViewModel
import com.orka.login.LoginScreenViewModel
import com.orka.main.MainViewModel
import com.orka.network.RemoteCategoriesDataSource
import com.orka.network.RemoteLoginDataSource
import com.orka.network.RemoteProductsDataSource
import com.orka.network.RemoteReceiveDataSource
import com.orka.network.RemoteStockDataSource
import com.orka.products.ProductsScreenViewModel
import com.orka.stock.StockScreenViewModel
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class AppContainer(private val context: Context) {

    private var homeViewModel: HomeScreenViewModel? = null

    private val baseUrl = "http://45.90.216.251:7000/api/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val userInfoDataSource: UserInfoDataSource by lazy {
        UserInfoDataSourceAdapter(FinancesDb.getDatabase(context).userInfoDao())
    }

    private val httpService: HttpService by lazy { HttpServiceImpl(mainViewModel) }
    val mainViewModel = MainViewModel(userInfoDataSource)
    private val credentialsDataSource: CredentialsDataSource by lazy {
        RemoteLoginDataSource.create(retrofit)
    }

    fun loginScreenViewModel(): LoginScreenViewModel {
        return LoginScreenViewModel(
            credentialsDataSource,
            credentialsManager = mainViewModel,
            httpService = httpService
        )
    }

    fun homeScreenViewModel(credential: Credential, navigate: (Int) -> Unit): HomeScreenViewModel {
        return homeViewModel ?: HomeScreenViewModel(
            dataSource = categoriesDataSource(credential),
            httpService = httpService,
            navigate = navigate
        ).apply { homeViewModel = this }
    }

    private fun categoriesDataSource(credential: Credential): CategoriesDataSource {
        return RemoteCategoriesDataSource.create(retrofit, credential)
    }

    fun stockScreenViewModel(credential: Credential, categoryId: Int, navigate: (Int) -> Unit): StockScreenViewModel {
        return StockScreenViewModel(
            categoryId = categoryId,
            httpService = httpService,
            stockDataSource = stockDataSource(credential),
            receiveDataSource = receiveDataSource(credential),
            productsDataSource = productsDataSource(credential),
            navigate = navigate
        )
    }

    private fun receiveDataSource(credential: Credential): ReceiveDataSource {
        return RemoteReceiveDataSource.create(retrofit, credential)
    }

    private fun stockDataSource(credential: Credential): StockDataSource {
        return RemoteStockDataSource.create(retrofit, credential)
    }

    fun productsViewModel(categoryId: Int, credential: Credential): ProductsScreenViewModel {
        return ProductsScreenViewModel(
            dataSource = productsDataSource(credential),
            categoryId = categoryId,
            httpService = httpService
        )
    }

    private fun productsDataSource(credential: Credential): ProductsDataSource {
        return RemoteProductsDataSource.create(retrofit, credential)
    }

    fun historyViewModel(credential: Credential): HistoryScreenViewModel {
        return HistoryScreenViewModel(
            dataSource = receiveDataSource(credential),
            httpService = httpService
        )
    }
}