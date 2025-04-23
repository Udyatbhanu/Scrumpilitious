package com.bhanu.baliyar.scrumpilitious.data

import com.bhanu.baliyar.scrumpilitious.core.ResultWrapper
import com.bhanu.baliyar.scrumpilitious.core.dispatchers.DispatcherProvider
import com.bhanu.baliyar.scrumpilitious.data.repository.RecipeRepository
import com.bhanu.baliyar.scrumpilitious.data.repository.RecipeRepositoryImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RecipeRepositoryImplTest {

    private lateinit var repository: RecipeRepository
    private lateinit var testDispatcher: TestDispatcher
    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var testScheduler: TestCoroutineScheduler


    @Before
    fun setUp() {
        testScheduler = TestCoroutineScheduler()
        testDispatcher = StandardTestDispatcher(testScheduler)
        dispatcherProvider = object : DispatcherProvider {
            override val io = testDispatcher
            override val default = testDispatcher
            override val main = testDispatcher
            override val unconfined = testDispatcher
        }
    }

    @Test
    fun `getRecipes returns Success when API call succeeds`() = runTest(testScheduler)  {
        // Arrange
        val fakeApi = FakeRecipeApi(shouldFail = false)
        repository = RecipeRepositoryImpl(dispatcherProvider, fakeApi)

        // Act
        val result = repository.getRecipes()

        // Assert
        assert(result is ResultWrapper.Success)
        val recipes = (result as ResultWrapper.Success).response.recipes
        assert(recipes.isNotEmpty())
        assertEquals("Fake Dish", recipes.first().name)
    }

    @Test
    fun `getRecipes returns Error when API call fails`() = runTest(testScheduler) {
        // Arrange
        val fakeApi = FakeRecipeApi(shouldFail = true)
        repository = RecipeRepositoryImpl(dispatcherProvider, fakeApi)

        // Act
        val result = repository.getRecipes()

        // Assert
        assert(result is ResultWrapper.Error)
    }
}