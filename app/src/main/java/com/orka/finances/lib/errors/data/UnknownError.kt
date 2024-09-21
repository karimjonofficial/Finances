package com.orka.finances.lib.errors.data

class UnknownError(private val description: String) : DataSourceError {
    override fun getDescription(): String {
        return description
    }
}