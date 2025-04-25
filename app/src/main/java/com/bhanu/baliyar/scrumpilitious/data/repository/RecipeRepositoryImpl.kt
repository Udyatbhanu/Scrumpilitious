package com.bhanu.baliyar.scrumpilitious.data.repository

import com.bhanu.baliyar.scrumpilitious.core.ResultWrapper
import com.bhanu.baliyar.scrumpilitious.core.dispatchers.DispatcherProvider
import com.bhanu.baliyar.scrumpilitious.core.safeApiCall
import com.bhanu.baliyar.scrumpilitious.data.mdoels.RecipesResponse
import com.bhanu.baliyar.scrumpilitious.data.network.RecipeApi

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RecipeRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val api: RecipeApi
) : RecipeRepository {

    override  fun getRecipes(): Flow<ResultWrapper<RecipesResponse>> {
        return safeApiCall(coroutineDispatcher = dispatcherProvider.io) {
            api.getRecipes()
        }
    }
}

