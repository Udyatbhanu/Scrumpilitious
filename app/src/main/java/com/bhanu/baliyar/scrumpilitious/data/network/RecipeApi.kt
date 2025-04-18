package com.bhanu.baliyar.scrumpilitious.data.network

import com.bhanu.baliyar.scrumpilitious.data.mdoels.RecipesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {

    @GET("recipes")
    suspend fun getRecipes(
        @Query("limit") limit: Int = 10,
        @Query("skip") skip: Int = 0,
        @Query("select") select: String = "name,image"
    ): Response<RecipesResponse>
}