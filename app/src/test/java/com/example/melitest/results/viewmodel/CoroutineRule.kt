package com.example.melitest.results.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class CoroutineRule(val testCoroutineDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()) :
    TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        testCoroutineDispatcher.cleanupTestCoroutines()
    }
}

@ExperimentalCoroutinesApi
fun CoroutineRule.runBlockingTest(block: suspend () -> Unit) =
    this.testCoroutineDispatcher.runBlockingTest {
        block()
    }