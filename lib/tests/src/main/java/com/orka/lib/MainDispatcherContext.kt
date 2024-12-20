package com.orka.lib

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll

abstract class MainDispatcherContext {
    companion object {
        @JvmStatic
        @OptIn(ExperimentalCoroutinesApi::class)
        @BeforeAll
        fun setMainDispatcher() {
            Dispatchers.setMain(Dispatchers.Unconfined)
        }

        @JvmStatic
        @OptIn(ExperimentalCoroutinesApi::class)
        @AfterAll
        fun resetMainDispatcher() {
            Dispatchers.resetMain()
        }
    }
}