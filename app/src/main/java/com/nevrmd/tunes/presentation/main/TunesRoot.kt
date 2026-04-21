package com.nevrmd.tunes.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.nevrmd.tunes.presentation.detail.DetailScreen
import com.nevrmd.tunes.presentation.navigation.Screen
import com.nevrmd.tunes.presentation.search.SearchScreen

@Composable
fun TunesRoot() {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Search
        ) {
            composable<Screen.Search> {
                SearchScreen(onNavigateToDetail = { item ->
                    navController.navigate(Screen.Detail(item.id))
                })
            }
            composable<Screen.Detail> { backStackEntry ->
                val args: Screen.Detail = backStackEntry.toRoute()
                DetailScreen(args = args, onBackClick = {
                    navController.popBackStack()
                })
            }
        }
    }
}