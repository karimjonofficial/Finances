package com.orka.finances.lib.data

interface CredentialsDataSource {
    fun get(): UserCredentials
    fun set(credentials: UserCredentials)
}