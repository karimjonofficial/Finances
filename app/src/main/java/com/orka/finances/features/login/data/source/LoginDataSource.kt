package com.orka.finances.features.login.data.source

import com.orka.finances.features.login.data.models.UserCredentials
import com.orka.finances.lib.errors.data.sources.DataSourceError

interface LoginDataSource {
    suspend fun getCredentials(
        username: String,
        password: String
    ): Pair<UserCredentials?, DataSourceError>
}