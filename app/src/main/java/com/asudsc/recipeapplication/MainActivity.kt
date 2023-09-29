package com.asudsc.recipeapplication

import RecipeCard
import SharedViewModel
import android.os.Bundle
import androidx.activity.*
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asudsc.recipeapplication.model.Recipe
import com.asudsc.recipeapplication.ui.components.CustomTopBar
import com.asudsc.recipeapplication.ui.theme.RecipeApplicationTheme
import recipes

class MainActivity : ComponentActivity() {
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeApplicationTheme {
                MyApp(
                    modifier = Modifier.fillMaxSize(),
                    sharedViewModel = sharedViewModel,
                    recipeList = recipes
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyApp(
    modifier: Modifier = Modifier,
    sharedViewModel: SharedViewModel,
    recipeList: List<Recipe>
) {
    Scaffold(
        topBar = {
            CustomTopBar(sharedViewModel = sharedViewModel)
        },
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(
                top = (padding.calculateTopPadding() + 10.dp),
                start = 10.dp,
                end = 10.dp,
                bottom = 20.dp
            ),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            val filteredRecipes = filterRecipes(sharedViewModel.searchTextState.value, recipeList)
            for (recipe in filteredRecipes) {
                item {
                    RecipeCard(recipe = recipe)
                }
            }

        }
    }
}

fun filterRecipes(searchText: String, recipeList: List<Recipe>): List<Recipe> {
    val lowercaseSearchText = searchText.lowercase()
    return recipeList.filter { recipe ->
        recipe.name.lowercase().contains(lowercaseSearchText)
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeAppPreview() {
    RecipeApplicationTheme {
        MyApp(
            sharedViewModel = SharedViewModel(),
            recipeList = recipes
        )
    }
}