package com.orka.finances.lib.data.info

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "info")
data class UserInfo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("token") val token: String? = null,
    @ColumnInfo("refresh") val refresh: String? = null
)
