package com.orka.finances

import com.orka.finances.lib.data.Credentials
import com.orka.finances.lib.data.CredentialsDataSource

class LocalCredentialsDataSource(private var credentials: Credentials) : CredentialsDataSource {
    override fun get(): Credentials {
        return credentials
    }

    override fun set(credentials: Credentials) {
        this.credentials = credentials
    }
}