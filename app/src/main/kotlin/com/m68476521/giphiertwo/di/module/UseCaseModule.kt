package com.m68476521.giphiertwo.di.module

import com.morozco.domain.giftevents.CategoriesRepository
import com.morozco.domain.giftevents.GiftRepositoryInterface
import com.morozco.domain.giftevents.GiftUseCase
import com.morozco.domain.giftevents.HomeRepository
import com.morozco.domain.giftevents.SearchRepository
import com.morozco.domain.repository.LocalImageRepository
import com.morozco.domain.repository.SaveImageUseCase
import com.morozco.presentation.categories.domain.CategoriesUseCase
import com.morozco.presentation.dashboard.domain.HomeUseCase
import com.morozco.presentation.search.domain.SearchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    fun provideGiftUserCase(repository: GiftRepositoryInterface): GiftUseCase = GiftUseCase(repository)

    @Provides
    @Singleton
    fun provideHomeUseCase(repository: HomeRepository): HomeUseCase = HomeUseCase(repository)

    @Provides
    @Singleton
    fun provideSearchUseCase(repository: SearchRepository): SearchUseCase = SearchUseCase(repository)

    @Provides
    @Singleton
    fun provideCategoriesUseCase(repository: CategoriesRepository): CategoriesUseCase = CategoriesUseCase(repository)

    @Provides
    @Singleton
    fun provideSaveImageUseCase(repository: LocalImageRepository): SaveImageUseCase = SaveImageUseCase(repository)
}
