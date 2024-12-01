package com.orka.finances.application.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.orka.finances.lib.data.info.UserInfo
import com.orka.finances.lib.data.info.UserInfoDataSource

@Dao
interface UserInfoDao : UserInfoDataSource {
    @Insert
    override suspend fun insert(userData: UserInfo)

    @Update
    override suspend fun update(userData: UserInfo)

    @Query("select * from info where id = 1")
    override suspend fun select(): UserInfo?

    @Query("update info set token = null, refresh = null where id = 1")
    override suspend fun unauthorize()
    //TODO Mess in there. The db interface should be separated from a common interface.
    // Make the data source common, and write a concrete class takes dao as an argument
}