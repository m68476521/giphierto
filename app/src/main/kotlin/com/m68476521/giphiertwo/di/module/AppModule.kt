package com.m68476521.giphiertwo.di.module

import com.morozco.domain.giftevents.GiftRepositoryInterface
import com.morozco.domain.giftevents.GiftUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideGiphRepository(

    )

    @Provides
    @Singleton
    fun provideGiftUserCase(
        repository: GiftRepositoryInterface
    ): GiftUseCase {
        return GiftUseCase(repository)

    }
}