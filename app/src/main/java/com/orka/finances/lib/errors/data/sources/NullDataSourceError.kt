package com.orka.finances.lib.errors.data.sources

object NullDataSourceError: DataSourceError {
    override fun getDescription(): String {
        return "NullDataSourceError"
    }
}