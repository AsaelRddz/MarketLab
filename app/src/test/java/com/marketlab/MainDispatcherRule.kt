package com.marketlab

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    val dispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {

    // se ejecuta antes de cada prueba.
    override fun starting(description: Description) {
        Dispatchers.setMain(dispatcher)
    }

    // se ejecuta al terminar cada prueba, incluso si falla.
    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}