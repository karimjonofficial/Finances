package com.orka.finances.lib.errors.data.sources

class UnknownDataSourceError : DataSourceError {
    override fun getDescription(): String {
        return "Unknown error"
    }
}