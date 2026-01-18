package com.m68476521.giphiertwo.di.module

import com.m68476521.giphiertwo.navigation.AppNavigator
import com.morozco.domain.navigation.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule  {
    @Provides
    @Singleton
    fun providesNavigator(): Navigator {
        return AppNavigator()
    }
}