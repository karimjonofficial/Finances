package com.orka.finances

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.orka.finances.features.login.data.sources.LoginDataSource
import com.orka.finances.features.login.data.sources.network.LoginApiService
import com.orka.finances.features.login.data.sources.network.RemoteLoginDataSource
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

private const val BASE_URL = "http://185.204.2.105/api/"

class AppContainer {
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val loginApiService: LoginApiService by lazy {
        retrofit.create(LoginApiService::class.java)
    }

    val loginDataSource: LoginDataSource = RemoteLoginDataSource(loginApiService)
}