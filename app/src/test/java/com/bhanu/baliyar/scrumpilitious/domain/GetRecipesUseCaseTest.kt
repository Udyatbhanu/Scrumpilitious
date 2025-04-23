
package com.bhanu.baliyar.scrumpilitious.domain

import com.bhanu.baliyar.scrumpilitious.core.ResultWrapper
import com.bhanu.baliyar.scrumpilitious.data.mdoels.Recipe
import com.bhanu.baliyar.scrumpilitious.data.mdoels.RecipesResponse
import com.bhanu.baliyar.scrumpilitious.data.repository.RecipeRepository
import com.bhanu.baliyar.scrumpilitious.domain.usecases.GetRecipesUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetRecipesUseCaseTest {

    private lateinit var recipeRepository: RecipeRepository
    private lateinit var getRecipesUseCase: GetRecipesUseCase

    @Before
    fun setup() {
        recipeRepository = mockk()
        getRecipesUseCase = GetRecipesUseCase(recipeRepository)
    }

    @Test
    fun `invoke should return list of recipes`() = runTest {
        // Arrange
        val mockRecipes = listOf(
            Recipe(id = 1, name = "Test", image = "", caloriesPerServing = 100,
                cookTimeMinutes = 20, cuisine = "", difficulty = "", instructions = emptyList(),
                ingredients = emptyList(), mealType = emptyList(), prepTimeMinutes = 5,
                rating = 4.5, reviewCount = 10, servings = 2, tags = emptyList(), userId = 1)
        )
        val response = RecipesResponse(
            recipes = mockRecipes,
            limit = 0,
            skip = 10,
            total = 200
        )
        val resultWrapper = ResultWrapper.Success(response)
        coEvery { recipeRepository.getRecipes() } returns resultWrapper

        // Act
        val result = getRecipesUseCase()

        // Assert
        assert(result is ResultWrapper.Success)
        val successResult = result as ResultWrapper.Success
        assertEquals(mockRecipes, successResult.response.recipes)
        coVerify { recipeRepository.getRecipes() }
    }
}