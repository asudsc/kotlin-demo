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
import com.asudsc.recipeapplication.ui.components.CustomTopBar
import com.asudsc.recipeapplication.ui.components.SearchAppBarState
import com.asudsc.recipeapplication.ui.theme.RecipeApplicationTheme
import recipes

class MainActivity : ComponentActivity() {
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeApplicationTheme {
                val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
                val searchTextState: String by sharedViewModel.searchTextState

                MyApp(
                    modifier = Modifier.fillMaxSize(),
                    sharedViewModel = sharedViewModel,
                    searchAppBarState = searchAppBarState,
                    searchTextState = searchTextState,
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
    searchAppBarState: SearchAppBarState,
    searchTextState: String,
    recipeList: List<Recipe>
) {
    Scaffold(
        topBar = {
            CustomTopBar(
                sharedViewModel = sharedViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = modifier.padding(
                top = (padding.calculateTopPadding() + 10.dp),
                start = 10.dp,
                end = 10.dp
            ),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            for (recipe in recipeList) {
                item {
                    RecipeCard(recipe = recipe)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RecipeApplicationTheme {
        MyApp(
            searchAppBarState = SearchAppBarState.CLOSED,
            sharedViewModel = SharedViewModel(),
            searchTextState = "",
            recipeList = recipes
        )
    }
}