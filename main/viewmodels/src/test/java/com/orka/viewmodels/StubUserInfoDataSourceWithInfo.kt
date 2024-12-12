package com.orka.viewmodels

import com.orka.data.UserInfo
import com.orka.data.UserInfoDataSource
import com.orka.lib.ID
import com.orka.lib.REFRESH
import com.orka.lib.TOKEN

class StubUserInfoDataSourceWithInfo : UserInfoDataSource {
    override suspend fun add(userData: UserInfo) {
        TODO("Not yet implemented")
    }

    override suspend fun update(userData: UserInfo) {
        TODO("Not yet implemented")
    }

    override suspend fun get(): UserInfo {
        return UserInfo(ID, TOKEN, REFRESH)
    }

    override suspend fun unauthorize() {
        TODO("Not yet implemented")
    }

}
