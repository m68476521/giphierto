package com.m68476521.giphiertwo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.m68476521.giphiertwo.navigation.Screen
import com.m68476521.giphiertwo.ui.theme.GiphiertwoTheme
import com.morozco.domain.navigation.NavigationEvent
import com.morozco.domain.navigation.Navigator
import com.morozco.presentation.dashboard.DashboardScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GiphiertwoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                    ) {
                        val navController = rememberNavController()

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
                        }
                    }
                }
            }
        }
    }
}
