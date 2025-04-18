package com.bhanu.baliyar.scrumpilitious.presentation.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.bhanu.baliyar.scrumpilitious.data.mdoels.Recipe

@Composable
fun DetailsScreen(recipe: Recipe?) {
    if (recipe != null) {
        Text(text = "Details Screen for ${recipe.name}")
    }

}