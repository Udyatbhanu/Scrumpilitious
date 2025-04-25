package com.bhanu.baliyar.scrumpilitious.data.repository

import com.bhanu.baliyar.scrumpilitious.core.ResultWrapper
import com.bhanu.baliyar.scrumpilitious.data.mdoels.Recipe
import com.bhanu.baliyar.scrumpilitious.data.mdoels.RecipesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRecipeRepository : RecipeRepository {

    var shouldFail = false

    override fun getRecipes(): Flow<ResultWrapper<RecipesResponse>> = flow {
        if (shouldFail) {
            emit(ResultWrapper.Error("Failed to fetch recipes"))
        } else {
            val recipes = listOf(
                Recipe(
                    id = 1,
                    name = "Fake Dish",
                    image = "https://via.placeholder.com/150.png",
                    caloriesPerServing = 100,
                    cookTimeMinutes = 20,
                    cuisine = "Test",
                    difficulty = "Easy",
                    instructions = listOf("Step 1", "Step 2"),
                    ingredients = listOf("Ingredient 1"),
                    mealType = listOf("Lunch"),
                    prepTimeMinutes = 10,
                    rating = 4.5,
                    reviewCount = 42,
                    servings = 2,
                    tags = listOf("test"),
                    userId = 1
                )
            )
            val response = RecipesResponse(
                limit = 10,
                recipes = recipes,
                skip = 0,
                total = 1
            )
            emit(ResultWrapper.Success(response))
        }
    }
}