package com.bhanu.baliyar.scrumpilitious.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.bhanu.baliyar.scrumpilitious.R
import com.bhanu.baliyar.scrumpilitious.core.AppLocalNavController
import com.bhanu.baliyar.scrumpilitious.core.AppLocalSpacing
import com.bhanu.baliyar.scrumpilitious.core.AppProvider
import com.bhanu.baliyar.scrumpilitious.core.navigation.ScreenRoute
import com.bhanu.baliyar.scrumpilitious.data.mdoels.Recipe

@Composable
fun RecipeCard(recipe: Recipe) {
    val navController = AppLocalNavController
    Card(
        modifier = Modifier
            .padding(AppLocalSpacing.medium)
            .fillMaxWidth()
            .height(200.dp)
            .clickable {
                navController.currentBackStackEntry?.savedStateHandle?.set("recipe", recipe)
                navController.navigate(ScreenRoute.Details.route)
            },
        shape = RoundedCornerShape(AppLocalSpacing.medium)
    ) {
        Column {
            RecipeImage(recipe.image)
            RecipeTitle(recipe.name)
        }
    }
}

@Composable
private fun RecipeImage(imageUrl : String){
    AsyncImage(
        model = imageUrl,
        contentDescription = "Image recipe",
        placeholder = painterResource(R.drawable.baseline_downloading_24),
        error = painterResource(R.drawable.error),
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth().height(150.dp).clip(RoundedCornerShape(AppLocalSpacing.large))
    )
}

@Composable
private fun RecipeTitle(name : String){
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .padding(AppLocalSpacing.medium)
                .align(Alignment.Center),
            text = name,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeCard() {
    AppProvider {
        RecipeCard(
            recipe = Recipe(
                id = 1,
                name = "Spicy Paneer Tikka",
                image = "https://via.placeholder.com/300.png",
                caloriesPerServing = 250,
                cookTimeMinutes = 30,
                cuisine = "Indian",
                difficulty = "Medium",
                instructions = listOf("Mix spices", "Grill paneer", "Serve hot"),
                ingredients = listOf("Paneer", "Spices", "Yogurt"),
                mealType = listOf("Dinner"),
                prepTimeMinutes = 15,
                rating = 4.5,
                reviewCount = 128,
                servings = 2,
                tags = listOf("spicy", "grill"),
                userId = 1
            )
        )
    }
}