package com.bhanu.baliyar.scrumpilitious.data.repository

import com.bhanu.baliyar.scrumpilitious.core.ResultWrapper
import com.bhanu.baliyar.scrumpilitious.core.dispatchers.DispatcherProvider
import com.bhanu.baliyar.scrumpilitious.core.safeApiCall
import com.bhanu.baliyar.scrumpilitious.data.mdoels.RecipesResponse
import com.bhanu.baliyar.scrumpilitious.data.network.RecipeApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

import javax.inject.Inject


class RecipeRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val api: RecipeApi
) : RecipeRepository {

    override suspend fun getRecipes(): ResultWrapper<RecipesResponse> {
        return safeApiCall(coroutineDispatcher = dispatcherProvider.io) {
            api.getRecipes()
        }
    }
}

