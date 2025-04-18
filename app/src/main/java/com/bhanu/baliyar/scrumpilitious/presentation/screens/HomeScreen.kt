package com.bhanu.baliyar.scrumpilitious.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search


import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.bhanu.baliyar.scrumpilitious.R
import com.bhanu.baliyar.scrumpilitious.core.LocalNavProvider
import com.bhanu.baliyar.scrumpilitious.core.LocalSpacing
import com.bhanu.baliyar.scrumpilitious.core.navigation.ScreenRoute
import com.bhanu.baliyar.scrumpilitious.data.mdoels.Recipe


sealed class HomeScreenState {
    data class Success(val recipes: List<Recipe>) : HomeScreenState()
    data class Error(val message: String) : HomeScreenState()
    object Loading : HomeScreenState()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchRecipes()
    }

    when (state) {
        is HomeScreenState.Success -> RecipesView(
            recipes = (state as HomeScreenState.Success).recipes
        )

        is HomeScreenState.Error -> TODO()
        HomeScreenState.Loading -> Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }
    }


}

@Composable
fun RecipesView(recipes: List<Recipe>) {
    var query by remember { mutableStateOf("") }
    val localSpacing = LocalSpacing.current
    Column {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = query,
            onValueChange = { query = it },
            maxLines = 1,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            },
            trailingIcon = {
                if (query.isNotBlank()) {
                    IconButton(onClick = { query = "" }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                    }
                }

            }
        )
        Spacer(modifier = Modifier.height(localSpacing.large))
        RecipesGrid(recipes)
    }
}


@Composable
fun RecipesGrid(recipes: List<Recipe>) {

    LazyVerticalGrid(
        modifier = Modifier,
        columns = GridCells.Fixed(2),
        userScrollEnabled = true
    ) {
        items(recipes) { recipe ->
            RecipeCard(recipe)
        }
    }
}

@Composable
fun RecipeCard(recipe: Recipe) {
    val navController = LocalNavProvider.current
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(200.dp)
            .clickable {
                navController.currentBackStackEntry?.savedStateHandle?.set("recipe", recipe)
                navController.navigate(ScreenRoute.Details.route)
            },
        shape = RoundedCornerShape(7.dp)
    ) {
        Column {
            AsyncImage(
                model = recipe.image,
                contentDescription = "Recipe",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                placeholder = painterResource(R.drawable.baseline_downloading_24),
                error = painterResource(R.drawable.error),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(topStart = 7.dp, topEnd = 7.dp))
            )
            Text(
                modifier = Modifier.padding(4.dp),
                text = recipe.name,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}