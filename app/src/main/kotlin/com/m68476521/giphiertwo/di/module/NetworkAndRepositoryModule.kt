package com.m68476521.giphiertwo.di.module

import com.m68476521.networking.MainAPI
import com.m68476521.networking.MainAPIInterface
import com.morozco.data.MainRepository
import com.morozco.data.NetworkCategoriesRepository
import com.morozco.data.NetworkHomeRepository
import com.morozco.data.NetworkSearchRepository
import com.morozco.domain.giftevents.CategoriesRepository
import com.morozco.domain.giftevents.GiftRepositoryInterface
import com.morozco.domain.giftevents.HomeRepository
import com.morozco.domain.giftevents.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkAndRepositoryModule {
    @Provides
    @Singleton
    fun provideGiphRepository(api: MainAPI): GiftRepositoryInterface = MainRepository(api)

    @Provides
    @Singleton
    fun provideHomeRepository(api: MainAPIInterface): HomeRepository = NetworkHomeRepository(api)

    @Provides
    @Singleton
    fun provideSearchRepository(api: MainAPIInterface): SearchRepository = NetworkSearchRepository(api)

    @Provides
    @Singleton
    fun provideCategoriesRepository(api: MainAPIInterface): CategoriesRepository = NetworkCategoriesRepository(api)
}
