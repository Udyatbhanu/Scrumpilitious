//
//package com.bhanu.baliyar.scrumpilitious.presentation.components
//
//import androidx.activity.ComponentActivity
//import androidx.compose.runtime.CompositionLocalProvider
//import androidx.compose.ui.test.junit4.createAndroidComposeRule
//import androidx.compose.ui.test.*
//import androidx.navigation.NavHostController
//import com.bhanu.baliyar.scrumpilitious.core.AppLocalNavController
//import com.bhanu.baliyar.scrumpilitious.core.AppLocalSpacing
//import com.bhanu.baliyar.scrumpilitious.core.AppSpacing
//import com.bhanu.baliyar.scrumpilitious.data.mdoels.Recipe
//import com.bhanu.baliyar.scrumpilitious.core.navigation.ScreenRoute
//import io.mockk.Runs
//import io.mockk.every
//import io.mockk.just
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import io.mockk.mockk
//import io.mockk.verify
//class RecipeCardMockTest {
//
//    @get:Rule
//    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
//
//    var mockNavController: NavHostController = mockk(relaxed = true)
//    @Before
//    fun setup() {
//        mockNavController = mockk(relaxed = true) // relaxed so it doesn't throw on unstubbed calls
//        every { mockNavController.navigate(ScreenRoute.Details.route) } just Runs
//        every { mockNavController.currentBackStackEntry } returns mockk {
//            every { savedStateHandle.set("recipe", any()) } just Runs
//        }
//    }
//
//    @Test
//    fun clickingRecipeCard_triggersNavigationAndSavesState() {
//        val testRecipe = Recipe(
//            id = 1,
//            name = "Mockk Paneer Tikka",
//            image = "https://via.placeholder.com/300.png",
//            caloriesPerServing = 250,
//            cookTimeMinutes = 30,
//            cuisine = "Indian",
//            difficulty = "Medium",
//            instructions = listOf("Step 1", "Step 2"),
//            ingredients = listOf("Mockk Ingredient"),
//            mealType = listOf("Dinner"),
//            prepTimeMinutes = 10,
//            rating = 4.5,
//            reviewCount = 123,
//            servings = 2,
//            tags = listOf("mock"),
//            userId = 1
//        )
//
//        composeTestRule.setContent {
//            CompositionLocalProvider(
//                AppLocalNavController provides mockNavController,
//                AppLocalSpacing provides AppSpacing()
//            ) {
//                RecipeCard(recipe = testRecipe)
//            }
//        }
//
//        composeTestRule.onNodeWithText("Mockk Paneer Tikka").performClick()
//
//        verify {
//            mockNavController.currentBackStackEntry?.savedStateHandle?.set("recipe", testRecipe)
//            mockNavController.navigate(ScreenRoute.Details.route)
//        }
//    }
//}