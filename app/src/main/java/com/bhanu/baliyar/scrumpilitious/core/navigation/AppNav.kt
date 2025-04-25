package com.bhanu.baliyar.scrumpilitious.core.navigation

import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bhanu.baliyar.scrumpilitious.core.LocalNavProvider
import com.bhanu.baliyar.scrumpilitious.data.mdoels.Recipe
import com.bhanu.baliyar.scrumpilitious.presentation.screens.DetailsScreen
import com.bhanu.baliyar.scrumpilitious.presentation.screens.HomeScreen


sealed class ScreenRoute(val route: String) {
    object RecipeHome : ScreenRoute("home")
    object Details : ScreenRoute("details")
}
@RequiresPermission(android.Manifest.permission.POST_NOTIFICATIONS)
@Composable
fun AppNav() {
    val navController = LocalNavProvider.current

    NavHost(
        navController = navController,
        startDestination = ScreenRoute.RecipeHome.route
    ) {
        composable(ScreenRoute.RecipeHome.route) {
            HomeScreen()
        }
        composable(ScreenRoute.Details.route) {
            val recipe =
                navController.previousBackStackEntry?.savedStateHandle?.get<Recipe>("recipe")
            DetailsScreen(recipe = recipe)
        }
    }
}