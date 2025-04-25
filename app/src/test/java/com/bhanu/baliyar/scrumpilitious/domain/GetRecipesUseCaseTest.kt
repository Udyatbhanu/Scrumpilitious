
package com.bhanu.baliyar.scrumpilitious.domain

import app.cash.turbine.test
import com.bhanu.baliyar.scrumpilitious.core.ResultWrapper

import com.bhanu.baliyar.scrumpilitious.data.repository.FakeRecipeRepository
import com.bhanu.baliyar.scrumpilitious.domain.usecases.GetRecipesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetRecipesUseCaseTest {

    private lateinit var useCase: GetRecipesUseCase
    private lateinit var fakeRepository: FakeRecipeRepository

    @Before
    fun setUp() {
        fakeRepository = FakeRecipeRepository()
        useCase = GetRecipesUseCase(fakeRepository)
    }

    @Test
    fun `invoke emits Success with data`() = runTest {
        useCase().test {
            val result = awaitItem()
            assertTrue(result is ResultWrapper.Success)
            val recipes = (result as ResultWrapper.Success).response.recipes

            assertEquals("Fake Dish", recipes.first().name)
            awaitComplete()
        }
    }

    @Test
    fun `invoke emits Error when repository fails`() = runTest {
        fakeRepository.shouldFail = true
        useCase().test {
            val result = awaitItem()
            assertTrue(result is ResultWrapper.Error)
            awaitComplete()
        }
    }
}