package com.orka.containers

import com.orka.core.UserInfoDataSource
import com.orka.database.info.UserInfoDao
import com.orka.database.info.UserInfoModel
import com.orka.info.UserInfo

internal class UserInfoDataSourceAdapter(private val userInfoDao: UserInfoDao) : UserInfoDataSource {
    override suspend fun add(userData: UserInfo) {
        val model = UserInfoModel(userData.id, userData.token, userData.refresh)
        userInfoDao.insert(model)
    }

    override suspend fun update(userData: UserInfo) {
        val model = UserInfoModel(userData.id, userData.token, userData.refresh)
        userInfoDao.update(model)
    }

    override suspend fun get(): UserInfo? {
        userInfoDao.select()?. let { return UserInfo(it.id, it.token, it.refresh) }
        return null
    }

    override suspend fun unauthorize() {
        userInfoDao.deleteCredentials()
    }
}