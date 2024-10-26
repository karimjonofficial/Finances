package com.orka.finances.lib.data

interface UserCredentialsDataSource {
    suspend fun getCredentials(): UserCredentials
    fun setCredentials(credentials: UserCredentials)
}