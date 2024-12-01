package com.orka.finances.lib.data.credentials.local

import com.orka.finances.lib.data.credentials.Credentials
import com.orka.finances.lib.data.credentials.CredentialsDataSource

class LocalCredentialsDataSource(private var credentials: Credentials) : CredentialsDataSource {
    override fun get(): Credentials {
        return credentials
    }

    override fun set(credentials: Credentials) {
        this.credentials = credentials
    }
}