package com.orka.finances.ui

import com.orka.data.UserInfo
import com.orka.data.UserInfoDataSource

class SpyUserInfoDataSourceOfInsert : UserInfoDataSource {
    var called = false

    override suspend fun add(userData: UserInfo) {
        called = true
    }

    override suspend fun update(userData: UserInfo) {
        TODO("Not yet implemented")
    }

    override suspend fun get(): UserInfo? {
        return null
    }

    override suspend fun unauthorize() {
        TODO("Not yet implemented")
    }
}
