package com.bhanu.baliyar.scrumpilitious.data

import com.bhanu.baliyar.scrumpilitious.data.mdoels.Recipe
import com.bhanu.baliyar.scrumpilitious.data.mdoels.RecipesResponse
import com.bhanu.baliyar.scrumpilitious.data.network.RecipeApi
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class FakeRecipeApi(private val shouldFail: Boolean = false) : RecipeApi {
    override suspend fun getRecipes(
        limit: Int,
        skip: Int,
        select: String
    ): Response<RecipesResponse> {
        return if (shouldFail) {
            Response.error(500, "".toResponseBody(null))
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
                    instructions = listOf("Test"),
                    ingredients = listOf("Fake"),
                    mealType = listOf("Lunch"),
                    prepTimeMinutes = 10,
                    rating = 4.5,
                    reviewCount = 42,
                    servings = 2,
                    tags = listOf("test"),
                    userId = 1
                )
            )
            Response.success(
                RecipesResponse(
                    limit = limit,
                    recipes = recipes,
                    skip = skip,
                    total = 100
                )
            )
        }
    }
}