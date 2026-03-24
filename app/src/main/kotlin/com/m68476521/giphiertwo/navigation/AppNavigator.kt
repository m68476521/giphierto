package com.m68476521.giphiertwo.navigation

import com.morozco.core.model.Screen
import com.morozco.domain.navigation.NavigationEvent
import com.morozco.domain.navigation.Navigator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class AppNavigator
    @Inject
    constructor() : Navigator {
        private val _navigationEvents = Channel<NavigationEvent>(Channel.BUFFERED)

        override val navigationEvents = _navigationEvents.receiveAsFlow()

        override suspend fun navigateBack() {
            _navigationEvents.trySend(NavigationEvent.NavigateBack)
        }

        override fun navigateToDashboard() {
            _navigationEvents.trySend(NavigationEvent.NavigateTo(Screen.Dashboard))
        }

        override fun navigateToSearch() {
            TODO("Not yet implemented")
        }

        override fun navigateToCategories() {
            TODO("Not yet implemented")
        }

        override fun navigateToSubCategories(subcategory: String) {
            _navigationEvents.trySend(NavigationEvent.NavigateTo(Screen.SubCategories(subcategory)))
        }
    }
