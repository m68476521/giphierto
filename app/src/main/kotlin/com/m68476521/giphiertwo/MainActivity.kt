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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.m68476521.giphiertwo.ui.theme.GiphiertwoTheme
import com.morozco.core.model.Screen
import com.morozco.domain.navigation.NavigationEvent
import com.morozco.domain.navigation.Navigator
import com.morozco.presentation.categories.CategoriesScreen
import com.morozco.presentation.dashboard.DashboardScreen
import com.morozco.presentation.search.SearchScreen
import com.morozco.presentation.subcategories.SubCategoriesScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigator: Navigator

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            // Observe the backstack to determine if we can show the back button
            val canPop by remember(navBackStackEntry) {
                derivedStateOf {
                    navController.previousBackStackEntry != null
                }
            }

            GiphiertwoTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = { Text("Giphiertwo") },
                            navigationIcon = {
                                if (canPop) {
                                    IconButton(onClick = { navController.popBackStack() }) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowBack,
                                            contentDescription = "Back",
                                        )
                                    }
                                }
                            },
                            actions = {
                                IconButton(onClick = { navController.navigate(Screen.Categories) }) {
                                    Icon(
                                        imageVector = Icons.Default.Category,
                                        contentDescription = "Categories",
                                    )
                                }
                                IconButton(
                                    onClick = { /* Handle settings click */ },
                                    enabled = false,
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Favorite,
                                        contentDescription = "Favorites",
                                    )
                                }
                            },
                        )
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
                                    is NavigationEvent.NavigateBack -> {
                                        navController.popBackStack()
                                    }

                                    is NavigationEvent.NavigateTo -> {
                                        navController.navigate(event.screen)
                                    }
                                }
                            }
                        }

                        NavHost(
                            navController = navController,
                            startDestination = Screen.Dashboard,
                        ) {
                            composable<Screen.Dashboard> {
                                DashboardScreen()
                            }
                            composable<Screen.Search> {
                                SearchScreen()
                            }
                            composable<Screen.Categories> {
                                CategoriesScreen()
                            }
                            composable<Screen.SubCategories> {
                                SubCategoriesScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}
