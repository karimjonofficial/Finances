package com.orka.finances

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.orka.finances.features.home.data.sources.network.CategoriesApiService
import com.orka.finances.features.home.data.sources.network.RemoteCategoriesDataSource
import com.orka.finances.features.home.presentation.viewmodels.HomeScreenViewModel
import com.orka.finances.features.login.data.sources.LoginDataSource
import com.orka.finances.features.login.data.sources.network.LoginApiService
import com.orka.finances.features.login.data.sources.network.RemoteLoginDataSource
import com.orka.finances.lib.data.UserCredentials
import com.orka.finances.lib.data.UserCredentialsDataSource
import com.orka.finances.lib.data.UserData
import com.orka.finances.lib.data.UserDataSource
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create

private const val BASE_URL = "http://185.204.2.105/api/"

class AppContainer(private val context: Context) {
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL).build()

    private val categoriesApiService: CategoriesApiService by createByRetrofit<CategoriesApiService>()
    private val categoriesDataSource = RemoteCategoriesDataSource(categoriesApiService)
    private var homeScreenViewModel: HomeScreenViewModel? = null
    private val loginApiService: LoginApiService by createByRetrofit<LoginApiService>()
    private val userDataDao: UserDataDao by lazy {
        FinancesDb.getDatabase(context).userDataDao()
    }
    val credentialsSource: UserCredentialsDataSource by lazy {
        LocalUserCredentialsDataSource(userDataDao)
    }
    val loginDataSource: LoginDataSource by lazy {
        RemoteLoginDataSource(loginApiService) {
            credentialsSource.setCredentials(UserCredentials(it.token, it.refresh))
        }
    }

    fun getHomeScreenViewModel(navigate: (Int) -> Unit): HomeScreenViewModel {
        return homeScreenViewModel ?: HomeScreenViewModel(
            dataSource = categoriesDataSource,
            credentialsSource = credentialsSource,
            passScreen = navigate
        ).also { homeScreenViewModel = it }
    }

    private inline fun <reified T> createByRetrofit(): Lazy<T> {
        return lazy { retrofit.create<T>() }
    }
}

private class LocalUserCredentialsDataSource(private val userDataDao: UserDataDao) : UserCredentialsDataSource {
    private var credentials: UserCredentials? = null

    override suspend fun getCredentials(): UserCredentials? {
        return credentials ?: getCredentialsFromDb()?.also { setCredentials(it) }
    }

    override suspend fun setCredentials(credentials: UserCredentials) {
        setCredentialsInDb(credentials)
        this.credentials = credentials
    }

    private suspend fun getCredentialsFromDb(): UserCredentials? {
        //TODO Write tests
        val data = userDataDao.getUserData()
        data?.let {
            if (it.token?.isNotBlank() == true) {
                val credentials = UserCredentials(it.token, it.refresh ?: "")
                return credentials
            }
        }
        return null
    }

    private suspend fun setCredentialsInDb(credentials: UserCredentials) {
        //TODO Write tests
        val data = userDataDao.getUserData()
        if(data == null) {
            val new = UserData(0, credentials.token, credentials.refresh)
            userDataDao.insert(new)
        } else {
            val new = data.copy(token = credentials.token, refresh = credentials.refresh)
            userDataDao.update(new)
        }
    }
}

@Dao
private interface UserDataDao : UserDataSource {
    @Insert
    override suspend fun insert(userData: UserData)

    @Update
    override suspend fun update(userData: UserData)

    @Query("select * from info where id = 1")
    override suspend fun getUserData(): UserData?
}

@Database(entities = [UserData::class], version = 1, exportSchema = false)
private abstract class FinancesDb : RoomDatabase() {
    abstract fun userDataDao(): UserDataDao

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