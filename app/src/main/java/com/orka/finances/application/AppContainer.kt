package com.orka.finances.application

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.orka.finances.application.database.FinancesDb
import com.orka.finances.features.home.data.sources.network.CategoriesApiService
import com.orka.finances.features.home.data.sources.network.RemoteCategoriesDataSource
import com.orka.finances.features.home.presentation.viewmodels.HomeScreenViewModel
import com.orka.finances.features.login.data.sources.LoginDataSource
import com.orka.finances.features.login.data.sources.network.LoginApiService
import com.orka.finances.features.login.data.sources.network.RemoteLoginDataSource
import com.orka.finances.features.stock.data.sources.network.RemoteStockDataSource
import com.orka.finances.features.stock.data.sources.network.StockApiService
import com.orka.finances.features.stock.presentation.viewmodels.StockScreenViewModel
import com.orka.finances.lib.data.credentials.Credential
import com.orka.finances.lib.data.info.UserInfoDataSource
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create

private const val BASE_URL = "http://45.90.216.251:7000/api/"

class AppContainer(private val context: Context) {

    private var homeScreenViewModel: HomeScreenViewModel? = null

    private inline fun <reified T : Any> createByRetrofit(): Lazy<T> {
        return lazy { retrofit.create<T>() }
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL).build()

    private val loginApiService: LoginApiService by createByRetrofit<LoginApiService>()
    private val categoriesApiService: CategoriesApiService by createByRetrofit<CategoriesApiService>()
    private val stockApiService: StockApiService by createByRetrofit<StockApiService>()

    val userInfoDataSource: UserInfoDataSource by lazy {
        FinancesDb.getDatabase(context).userDataDao()
    }

    val loginDataSource: LoginDataSource by lazy {
        RemoteLoginDataSource(loginApiService)
    }

    fun getHomeScreenViewModel(credential: Credential, navigate: (Int) -> Unit, unauthorize: () -> Unit): HomeScreenViewModel {
        var viewModel = homeScreenViewModel

        if(viewModel == null) {
            viewModel = HomeScreenViewModel(
                dataSource = RemoteCategoriesDataSource(categoriesApiService, credential),
                navigate = navigate,
                unauthorize = unauthorize
            )

            homeScreenViewModel = viewModel
        }
        return viewModel
    }

    fun getStockScreenViewModel(credential: Credential, categoryId: Int, navigate: (Int) -> Unit, unauthorize: () -> Unit): StockScreenViewModel {
        val dataSource = RemoteStockDataSource(stockApiService, credential)
        return StockScreenViewModel(categoryId, dataSource, navigate, unauthorize)
    }
}

