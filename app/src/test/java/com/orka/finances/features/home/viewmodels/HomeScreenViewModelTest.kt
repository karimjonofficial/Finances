package com.orka.finances.features.home.viewmodels

import com.orka.finances.Counter
import com.orka.finances.MainDispatcherRule
import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.models.Category
import com.orka.finances.features.home.presentation.viewmodels.HomeScreenViewModel
import com.orka.finances.lib.data.credentials.Credentials
import com.orka.finances.lib.data.credentials.CredentialsDataSource
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.BufferedSource
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

private const val TOKEN = "token"
private const val REFRESH = "refresh"
private val CREDENTIALS = Credentials(TOKEN, REFRESH)

class HomeScreenViewModelTest {
    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun callsGetCredentialsWhenFetching() {
        val credentialsDataSource = SpyCredentialsDataSource()
        val dataSource = DummyCategoriesDataSource()
        val count = credentialsDataSource.counter.count
        val viewModel = HomeScreenViewModel(dataSource, credentialsDataSource, {}) {}
        viewModel.fetchData()
        assertEquals(count + 1, credentialsDataSource.counter.count)
    }

    @Test
    fun initializesDataAsCreated() = runTest {
        val credentialsDataSource = DummyCredentialsDataSource()
        val dataSource = StubCategoriesDataSource()
        val viewModel = HomeScreenViewModel(dataSource, credentialsDataSource, {}) {}
        assertEquals(dataSource.initialData, viewModel.uiState.value)
    }

    @Test
    fun setsUiEmptyWhenNoDataFetched() = runTest {
        val credentialsDataSource = DummyCredentialsDataSource()
        val dataSource = DummyCategoriesDataSource()
        val viewModel = HomeScreenViewModel(dataSource, credentialsDataSource, {}){}
        assertEquals(0, viewModel.uiState.value.size)
    }

    @Test
    fun callsPassScreenWhenSelectedCategory() = runTest {
        val credentialsDataSource = DummyCredentialsDataSource()
        val counter = Counter()
        val dataSource = DummyCategoriesDataSource()
        val viewModel = HomeScreenViewModel(dataSource, credentialsDataSource, { counter.count() }) {}
        val category = Category(1, "Name", "Description")
        val count = counter.count

        viewModel.selectCategory(category)

        assertEquals(count + 1, counter.count)
    }

    @Test
    fun callsUnauthorizeWhenUserNotAuthorized() = runTest {
        val credentialsDataSource = DummyCredentialsDataSource()
        val counter = Counter()
        val dataSource = ThrowingCategoriesDataSource()
        val viewModel = HomeScreenViewModel(dataSource, credentialsDataSource, {}) { counter.count() }
        val count = counter.count

        viewModel.fetchData()

        assertEquals(count + 1, counter.count)
    }
}

class DummyCredentialsDataSource : CredentialsDataSource {
    override fun get(): Credentials {
        return CREDENTIALS
    }

    override fun set(credentials: Credentials) {}
}

class SpyCredentialsDataSource : CredentialsDataSource {
    val counter = Counter()

    override fun get(): Credentials {
        counter.count()
        return CREDENTIALS
    }

    override fun set(credentials: Credentials) {}
}

private class DummyCategoriesDataSource : CategoriesDataSource {
    override suspend fun get(token: String): List<Category>? {
        return null
    }
}

private class ThrowingCategoriesDataSource : CategoriesDataSource {
    override suspend fun get(token: String): List<Category>? {
        throw HttpException(Response.error<String>(401, NullResponseBody()))
    }
}

private class StubCategoriesDataSource : CategoriesDataSource {
    val initialData: List<Category> = emptyList()

    override suspend fun get(token: String): List<Category> {
        return initialData
    }
}

private class NullResponseBody : ResponseBody() {
    override fun contentLength(): Long {
        return 0
    }

    override fun contentType(): MediaType? {
        return null
    }

    override fun source(): BufferedSource {
        TODO("Not implemented yet")
    }

}