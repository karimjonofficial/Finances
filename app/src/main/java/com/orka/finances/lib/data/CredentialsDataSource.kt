package com.orka.finances.lib.data

interface CredentialsDataSource {
    fun get(): Credentials
    fun set(credentials: Credentials)
}