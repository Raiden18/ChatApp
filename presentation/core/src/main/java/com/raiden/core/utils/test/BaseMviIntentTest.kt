package com.livetyping.beautyshop.core.testutils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raiden.core.utils.test.RxTestSchedulerRule
import org.junit.Rule

abstract class BaseMviIntentTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val testSchedulerRule = RxTestSchedulerRule()
}