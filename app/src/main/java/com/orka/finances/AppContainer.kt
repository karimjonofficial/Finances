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
import com.orka.finances.lib.data.Credentials
import com.orka.finances.lib.data.CredentialsDataSource
import com.orka.finances.lib.data.UserInfo
import com.orka.finances.lib.data.UserInfoDataSource
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
    private val loginApiService: LoginApiService by createByRetrofit<LoginApiService>()
    val infoDao: UserInfoDao by lazy {
        FinancesDb.getDatabase(context).userDataDao()
    }
    val loginDataSource: LoginDataSource by lazy {
        RemoteLoginDataSource(loginApiService) { credentials ->
            credentialsDataSource.let {
                if(it == null) {
                    credentialsDataSource = LocalCredentialsDataSource(credentials)
                } else  { it.set(credentials) }
            }
        }
    }
    var credentialsDataSource: CredentialsDataSource? = null

    private var homeScreenViewModel: HomeScreenViewModel? = null

    fun getHomeScreenViewModel(
        credentialsDataSource: CredentialsDataSource,
        passScreen: (Int) -> Unit,
        unauthorize: suspend () -> Unit
    ): HomeScreenViewModel {
        var viewModel = homeScreenViewModel
        if(viewModel == null) {
            viewModel = HomeScreenViewModel(
                dataSource = categoriesDataSource,
                credentialsDataSource = credentialsDataSource,
                passScreen = passScreen,
                unauthorize = unauthorize
            )

            homeScreenViewModel = viewModel
        }
        return viewModel
    }

    suspend fun initialize() {
        val data = infoDao.select()
        data?.let {
            if(it.token?.isNotBlank() == true) {
                val credentials = Credentials(it.token, it.refresh ?: "")
                credentialsDataSource = LocalCredentialsDataSource(credentials)
            }
        }
    }

    suspend fun unauthorize() {
        infoDao.unauthorize()
        credentialsDataSource = null
    }

    private inline fun <reified T> createByRetrofit(): Lazy<T> {
        return lazy { retrofit.create<T>() }
    }
}

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
}

@Database(entities = [UserInfo::class], version = 1, exportSchema = false)
private abstract class FinancesDb : RoomDatabase() {
    abstract fun userDataDao(): UserInfoDao

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

