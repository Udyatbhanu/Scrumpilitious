package com.bhanu.baliyar.scrumpilitious.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bhanu.baliyar.scrumpilitious.R

data class AppSpacing(val small: Dp = 4.dp, val medium: Dp = 8.dp, val large: Dp = 16.dp)

val LocalSpacing = staticCompositionLocalOf { AppSpacing() }
val LocalNavProvider = compositionLocalOf<NavHostController> { error("NavHost not provided") }

val AppLocalSpacing: AppSpacing
    @Composable get() = LocalSpacing.current

val AppLocalNavController: NavHostController
    @Composable get() = LocalNavProvider.current

@Composable
fun rememberSpacing(): AppSpacing {
    val small = dimensionResource(R.dimen.spacing_small)
    val medium = dimensionResource(R.dimen.spacing_medium)
    val large = dimensionResource(R.dimen.spacing_large)

    return remember(small, medium, large) {
        AppSpacing(small = small, medium = medium, large = large)
    }
}


@Composable
fun AppProvider(content: @Composable () -> Unit) {
    val navController = rememberNavController()
    val localSpacing = rememberSpacing()
    CompositionLocalProvider(
        LocalNavProvider provides navController,
        LocalSpacing provides localSpacing
    ) {
        content()
    }
}