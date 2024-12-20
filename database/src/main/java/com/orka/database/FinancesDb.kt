package com.orka.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.orka.database.info.UserInfoDao
import com.orka.database.info.UserInfoModel

@Database(entities = [UserInfoModel::class], version = 1, exportSchema = false)
abstract class FinancesDb : RoomDatabase() {
    abstract fun userInfoDao(): UserInfoDao

    companion object {
        @Volatile
        private var Instance: FinancesDb? = null

        fun getDatabase(context: Context): FinancesDb {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, FinancesDb::class.java, "finances_db")
                    .fallbackToDestructiveMigration()
                    .build().also { Instance = it }
            }
        }
    }
}