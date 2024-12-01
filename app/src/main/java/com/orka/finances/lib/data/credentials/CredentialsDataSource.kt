package com.orka.finances.lib.data.credentials

interface CredentialsDataSource {
    fun get(): Credentials
    fun set(credentials: Credentials)
}