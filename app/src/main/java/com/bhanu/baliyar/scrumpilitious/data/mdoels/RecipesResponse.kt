package com.bhanu.baliyar.scrumpilitious.data.mdoels

data class RecipesResponse(
    val limit: Int,
    val recipes: List<Recipe>,
    val skip: Int,
    val total: Int
)