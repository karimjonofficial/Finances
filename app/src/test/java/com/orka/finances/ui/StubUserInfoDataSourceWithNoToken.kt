package com.orka.finances.ui

import com.orka.finances.ID
import com.orka.finances.lib.data.info.UserInfo
import com.orka.finances.lib.data.info.UserInfoDataSource

class StubUserInfoDataSourceWithNoToken : UserInfoDataSource {
    override suspend fun insert(userData: UserInfo) {
        TODO("Not yet implemented")
    }

    override suspend fun update(userData: UserInfo) {
        TODO("Not yet implemented")
    }

    override suspend fun select(): UserInfo {
        return UserInfo(id = ID, token = null, refresh = null)
    }

    override suspend fun unauthorize() {
        TODO("Not yet implemented")
    }
}