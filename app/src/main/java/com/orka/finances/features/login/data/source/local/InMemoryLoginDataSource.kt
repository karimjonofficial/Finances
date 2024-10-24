package com.orka.finances.features.login.data.source.local

import com.orka.finances.features.login.data.models.UserCredentials
import com.orka.finances.features.login.data.source.LoginDataSource
import com.orka.finances.lib.errors.data.sources.DataSourceError
import com.orka.finances.lib.errors.data.sources.NullDataSourceError
import com.orka.finances.lib.errors.data.sources.UnknownDataSourceError

class InMemoryLoginDataSource : LoginDataSource {
    override suspend fun getCredentials(
        username: String,
        password: String
    ): Pair<UserCredentials?, DataSourceError> {
        return if(username == "admin" && password == "123")
            Pair(UserCredentials("token", "access"), NullDataSourceError)
        else Pair(null, UnknownDataSourceError())
    }
}