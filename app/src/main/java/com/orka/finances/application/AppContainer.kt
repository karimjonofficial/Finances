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
import com.orka.finances.lib.data.credentials.CredentialsDataSource
import com.orka.finances.lib.data.info.UserInfoDataSource
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create

private const val BASE_URL = "http://45.90.216.251:7000/api/"

class AppContainer(private val context: Context) {
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL).build()

    private val categoriesApiService: CategoriesApiService by createByRetrofit<CategoriesApiService>()
    private val categoriesDataSource = RemoteCategoriesDataSource(categoriesApiService)
    private val loginApiService: LoginApiService by createByRetrofit<LoginApiService>()

    val userInfoDataSource: UserInfoDataSource by lazy {
        FinancesDb.getDatabase(context).userDataDao()
    }

    val loginDataSource: LoginDataSource by lazy {
        RemoteLoginDataSource(loginApiService)
    }

    private var homeScreenViewModel: HomeScreenViewModel? = null

    fun getHomeScreenViewModel(
        credentialsDataSource: CredentialsDataSource,
        passScreen: (Int) -> Unit,
        unauthorize: suspend () -> Unit
    ): HomeScreenViewModel {
        var viewModel = homeScreenViewModel
        if(viewModel == null) {
            viewModel = HomeScreenViewModel(
                dataSource = categoriesDataSource,
                credentialsDataSource = credentialsDataSource,
                passScreen = passScreen,
                unauthorize = unauthorize
            )

            homeScreenViewModel = viewModel
        }
        return viewModel
    }

    suspend fun unauthorize() {
        userInfoDataSource.unauthorize()
    }

    private inline fun <reified T> createByRetrofit(): Lazy<T> {
        return lazy { retrofit.create<T>() }
    }
}

