package com.orka.finances.features.login.data.source.network

import android.util.Log
import com.orka.finances.features.login.data.models.UserCredentials
import com.orka.finances.features.login.data.source.LoginDataSource
import com.orka.finances.lib.errors.data.sources.DataSourceError
import com.orka.finances.lib.errors.data.sources.NullDataSourceError
import com.orka.finances.lib.errors.data.sources.UnknownDataSourceError

class RemoteLoginDataSource(private val apiService: LoginApiService) : LoginDataSource {
    override suspend fun getCredentials(username: String, password: String): Pair<UserCredentials?, DataSourceError> {
        try {
            val credentials = apiService.access(username, password)
            val error = if (credentials != null) NullDataSourceError else UnknownDataSourceError("No such user")
            Log.d("FinancesApp.HttpRequest", credentials?.let { "Access: ${it.access}\nRefresh: ${it.refresh}"} ?: "NULL")
            return Pair(credentials, error)
        } catch (e: Exception) {
            Log.d("FinancesApp.HttpRequest", e.message.toString())
            return Pair(null, UnknownDataSourceError("Error: ${e.message.toString()}"))
        }
    }
}