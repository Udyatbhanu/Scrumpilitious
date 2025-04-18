package com.bhanu.baliyar.scrumpilitious.data.repository

import com.bhanu.baliyar.scrumpilitious.core.ResultWrapper
import com.bhanu.baliyar.scrumpilitious.data.mdoels.RecipesResponse

interface RecipeRepository {

    suspend fun getRecipes(): ResultWrapper<RecipesResponse>
}