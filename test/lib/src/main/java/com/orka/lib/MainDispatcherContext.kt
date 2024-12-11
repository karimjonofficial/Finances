package com.orka.lib

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll

abstract class MainDispatcherContext {
    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()
}