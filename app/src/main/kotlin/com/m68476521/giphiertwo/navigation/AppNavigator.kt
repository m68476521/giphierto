package com.m68476521.giphiertwo.navigation

import com.morozco.domain.NavigationEvent
import com.morozco.domain.Navigator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class AppNavigator @Inject constructor(): Navigator {
    private val _navigationEvents = Channel<NavigationEvent>(Channel.BUFFERED)

    override val navigationEvents = _navigationEvents.receiveAsFlow()

    override suspend fun navigateBack() {
        _navigationEvents.trySend(NavigationEvent.NavigateBack)
    }

    override suspend fun navigateToDashboard() {
        _navigationEvents.trySend(NavigationEvent.NavigateTo(Screen.Dashboard))
    }

}