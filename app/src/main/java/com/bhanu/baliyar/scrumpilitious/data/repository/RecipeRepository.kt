package com.bhanu.baliyar.scrumpilitious.data.repository

import com.bhanu.baliyar.scrumpilitious.core.ResultWrapper
import com.bhanu.baliyar.scrumpilitious.data.mdoels.RecipesResponse
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getRecipes(): Flow<ResultWrapper<RecipesResponse>>
}