package com.m68476521.giphiertwo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.m68476521.giphiertwo.ui.theme.GiphiertwoTheme
import com.morozco.core.model.Image
import com.morozco.core.model.Screen
import com.morozco.domain.navigation.NavigationEvent
import com.morozco.domain.navigation.Navigator
import com.morozco.presentation.categories.CategoriesScreen
import com.morozco.presentation.dashboard.DashboardScreen
import com.morozco.presentation.detail.DetailScreen
import com.morozco.presentation.favorites.FavoritesScreen
import com.morozco.presentation.search.SearchScreen
import com.morozco.presentation.subcategories.SubCategoriesScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.reflect.typeOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GiphiertwoTheme {
                giphierApp(navigator = navigator)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun giphierApp(
    navigator: Navigator,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val canPop by remember(navBackStackEntry) {
        derivedStateOf { navController.previousBackStackEntry != null }
    }
    var selectedScreen by remember { mutableStateOf(Screen.Dashboard) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            giphierTopAppBar(navController, canPop)
        },
        bottomBar = {
            giphierBottomAppBar(navController, selectedScreen)
        },
    ) { innerPadding ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            LaunchedEffect(Unit) {
                navigator.navigationEvents.collect { event ->
                    when (event) {
                        is NavigationEvent.NavigateBack -> navController.popBackStack()
                        is NavigationEvent.NavigateTo -> {
                            val screen = event.screen
                            if (screen is Screen) {
                                navController.navigate(screen as Any)
                            }
                        }
                    }
                }
            }

            NavHost(
                navController = navController,
                startDestination = Screen.Dashboard,
            ) {
                composable<Screen.Dashboard> { DashboardScreen() }
                composable<Screen.Search> { SearchScreen() }
                composable<Screen.Categories> { CategoriesScreen() }
                composable<Screen.SubCategories> { SubCategoriesScreen() }
                composable<Screen.Favorites> { FavoritesScreen() }
                composable<Screen.DetailItem>(
                    typeMap = mapOf(typeOf<Image>() to Image.NavigationType),
                ) {
                    DetailScreen()
                }
            }
        }
    }
}

// Top App Bar content - extracted from GiphierApp for better code organization
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun giphierTopAppBar(
    navController: NavHostController,
    canPop: Boolean,
) {
    CenterAlignedTopAppBar(
        colors =
            TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        title = { Text("Giphiertwo") },
        navigationIcon = {
            if (canPop) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        },
        /*
        actions = {
            IconButton(onClick = { navController.navigate(Screen.Categories) }) {
                Icon(Icons.Default.Category, contentDescription = "Categories")
            }
            IconButton(onClick = { navController.navigate(Screen.Favorites) }) {
                Icon(Icons.Default.Favorite, contentDescription = "Favorites")
            }
        },
         */
    )
}

// Bottom Navigation Bar content - extracted from GiphierApp for better code organization
@Composable
private fun giphierBottomAppBar(
    navController: NavHostController,
    selectedScreen: Screen,
) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        NavigationBarItem(
            selected = selectedScreen == Screen.Dashboard,
            onClick = { navController.navigate(Screen.Dashboard) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
        )
        NavigationBarItem(
            selected = selectedScreen == Screen.Categories,
            onClick = { navController.navigate(Screen.Categories) },
            icon = { Icon(Icons.Default.Category, contentDescription = "Categories") },
            label = { Text("Search") },
        )
        NavigationBarItem(
            selected = selectedScreen == Screen.Favorites,
            onClick = { navController.navigate(Screen.Favorites) },
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
            label = { Text("Favorites") },
        )
    }
}
