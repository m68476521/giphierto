package com.m68476521.giphiertwo.navigation

import com.morozco.core.model.Image
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

        override fun navigateToSearch(word: String) {
            _navigationEvents.trySend(NavigationEvent.NavigateTo(Screen.Search(word)))
        }

        override fun navigateToCategories() {
            TODO("Not yet implemented")
        }

        override fun navigateToSubCategories(subcategory: String) {
            _navigationEvents.trySend(NavigationEvent.NavigateTo(Screen.SubCategories(subcategory)))
        }

        override fun navigateToFavorites() {
            _navigationEvents.trySend(NavigationEvent.NavigateTo(Screen.Favorites))
        }

        override fun navigateToDetails(image: Image) {
            _navigationEvents.trySend(NavigationEvent.NavigateTo(Screen.DetailItem(image)))
        }
    }
