package com.orka.finances.features.home.viewmodels

import com.orka.finances.MainDispatcherRule
import com.orka.finances.features.home.data.sources.CategoriesDataSource
import com.orka.finances.features.home.models.Category
import com.orka.finances.features.home.presentation.viewmodels.HomeScreenViewModel
import com.orka.finances.lib.data.UserCredentials
import com.orka.finances.lib.data.UserCredentialsDataSource
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

private const val TOKEN = "token"
private const val REFRESH = "refresh"
private val CREDENTIALS = UserCredentials(TOKEN, REFRESH)

class HomeScreenViewModelTest {
    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun callsGetCredentialsWhenFetching() {
        val credentialsDataSource = SpyCredentialsDataSource()
        val dataSource = DummyCategoriesDataSource()
        val count = credentialsDataSource.counter.count
        HomeScreenViewModel(dataSource, credentialsDataSource) {}
        assertEquals(count + 1, credentialsDataSource.counter.count)
    }

    @Test
    fun initializesDataAsCreated() = runTest {
        val credentialsDataSource = DummyCredentialsDataSource()
        val dataSource = StubCategoriesDataSource()
        val viewModel = HomeScreenViewModel(dataSource, credentialsDataSource) {}
        assertEquals(dataSource.initialData, viewModel.uiState.value)
    }

    @Test
    fun setsUiEmptyWhenNoDataFetched() = runTest {
        val credentialsDataSource = DummyCredentialsDataSource()
        val dataSource = DummyCategoriesDataSource()
        val viewModel = HomeScreenViewModel(dataSource, credentialsDataSource){}
        assertEquals(0, viewModel.uiState.value.size)
    }

    @Test
    fun callsPassScreenWhenSelectedCategory() = runTest {
        val credentialsDataSource = DummyCredentialsDataSource()
        val counter = Counter()
        val dataSource = DummyCategoriesDataSource()
        val viewModel = HomeScreenViewModel(dataSource, credentialsDataSource) { counter.count() }
        val category = Category(1, "Name", "Description")
        val count = counter.count

        viewModel.selectCategory(category)

        assertEquals(count + 1, counter.count)
    }
}

private class DummyCategoriesDataSource : CategoriesDataSource {
    override suspend fun get(token: String): List<Category>? {
        return null
    }
}

private class StubCategoriesDataSource : CategoriesDataSource {
    val initialData: List<Category> = emptyList()

    override suspend fun get(token: String): List<Category> {
        return initialData
    }
}

private class DummyCredentialsDataSource : UserCredentialsDataSource {
    override fun getCredentials(): UserCredentials {
        return CREDENTIALS
    }

    override fun setCredentials(credentials: UserCredentials) {
        TODO("Not yet implemented")
    }
}

private class SpyCredentialsDataSource : UserCredentialsDataSource {
    val counter = Counter()

    override fun getCredentials(): UserCredentials {
        counter.count()
        return CREDENTIALS
    }

    override fun setCredentials(credentials: UserCredentials) {
        TODO("Not yet implemented")
    }
}

private class Counter {
    var count = 0
        private set

    fun count() { count++ }
}
