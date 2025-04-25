package com.bhanu.baliyar.scrumpilitious.data

import app.cash.turbine.test
import com.bhanu.baliyar.scrumpilitious.core.ResultWrapper
import com.bhanu.baliyar.scrumpilitious.core.dispatchers.DispatcherProvider
import com.bhanu.baliyar.scrumpilitious.data.repository.RecipeRepository
import com.bhanu.baliyar.scrumpilitious.data.repository.RecipeRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RecipeRepositoryImplTest {

    private lateinit var repository: RecipeRepository
    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var testScheduler: TestCoroutineScheduler

    @Before
    fun setUp() {
        testScheduler = TestCoroutineScheduler()
        val testDispatcher = StandardTestDispatcher(testScheduler)
        dispatcherProvider = object : DispatcherProvider {
            override val io = testDispatcher
            override val default = testDispatcher
            override val main = testDispatcher
            override val unconfined = testDispatcher
        }
    }

    @Test
    fun `getRecipes emits Success when API call succeeds`() = runTest(testScheduler) {
        val fakeApi = FakeRecipeApi(shouldFail = false)
        repository = RecipeRepositoryImpl(dispatcherProvider, fakeApi)

        repository.getRecipes().test {
            // Skip the loading emission
            val loading = awaitItem()
            assertTrue(loading is ResultWrapper.Loading)

            // Then expect success
            val result = awaitItem()
            assertTrue(result is ResultWrapper.Success)

            val recipes = (result as ResultWrapper.Success).response.recipes
            assertEquals("Fake Dish", recipes.first().name)

            awaitComplete()
        }
    }

    @Test
    fun `getRecipes emits Error when API call fails`() = runTest(testScheduler) {
        val fakeApi = FakeRecipeApi(shouldFail = true)
        repository = RecipeRepositoryImpl(dispatcherProvider, fakeApi)

        repository.getRecipes().test {
            val loading = awaitItem()
            assertTrue(loading is ResultWrapper.Loading)

            val result = awaitItem()
            assertTrue(result is ResultWrapper.Error)
            awaitComplete()
        }
    }
}