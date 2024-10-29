package com.orka.finances.lib.data

interface UserInfoDataSource {
    suspend fun insert(userData: UserInfo)
    suspend fun update(userData: UserInfo)
    suspend fun select(): UserInfo?
    suspend fun unauthorize()
}