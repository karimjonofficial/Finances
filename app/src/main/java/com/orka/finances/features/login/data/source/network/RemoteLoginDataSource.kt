package com.orka.finances.features.login.data.source.network

import com.orka.finances.features.login.data.models.UserCredentials
import com.orka.finances.features.login.data.source.LoginDataSource
import com.orka.finances.lib.errors.data.sources.DataSourceError
import com.orka.finances.lib.errors.data.sources.NullDataSourceError
import com.orka.finances.lib.errors.data.sources.UnknownDataSourceError

class RemoteLoginDataSource(private val apiService: LoginApiService) : LoginDataSource {
    override suspend fun getCredentials(username: String, password: String): Pair<UserCredentials?, DataSourceError> {
        val credentials = apiService.access(username, password)

        return if(credentials != null) {
            Pair(credentials, NullDataSourceError)
        } else {
            Pair(null, UnknownDataSourceError())
        }
    }
}