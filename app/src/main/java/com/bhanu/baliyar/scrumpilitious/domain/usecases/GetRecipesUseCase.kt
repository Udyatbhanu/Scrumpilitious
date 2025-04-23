package com.bhanu.baliyar.scrumpilitious.domain.usecases

import com.bhanu.baliyar.scrumpilitious.core.ResultWrapper
import com.bhanu.baliyar.scrumpilitious.data.mdoels.RecipesResponse
import com.bhanu.baliyar.scrumpilitious.data.repository.RecipeRepository
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(private val repository: RecipeRepository) {
    suspend operator fun invoke(): ResultWrapper<RecipesResponse> = repository.getRecipes()
}