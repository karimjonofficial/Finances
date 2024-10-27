package com.orka.finances.lib.data

interface UserCredentialsDataSource {
    suspend fun getCredentials(): UserCredentials?
    suspend fun setCredentials(credentials: UserCredentials)
}