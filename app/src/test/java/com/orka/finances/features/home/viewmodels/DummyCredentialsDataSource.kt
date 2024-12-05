package com.orka.finances.features.home.viewmodels

import com.orka.finances.CREDENTIAL
import com.orka.finances.lib.data.credentials.Credentials
import com.orka.finances.lib.data.credentials.CredentialsDataSource

class DummyCredentialsDataSource : CredentialsDataSource {
    override fun get(): Credentials {
        return CREDENTIAL
    }

    override fun set(credentials: Credentials) {}
}